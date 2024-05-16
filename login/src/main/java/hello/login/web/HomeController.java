package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/")
    public String home() {
        return "home";
    }


//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId",required = false)Long memberId, Model model) {
        if(memberId==null){
            return "home";
        }
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember==null){
            return "home";
        }
        model.addAttribute("member",loginMember);
        return "loginHome";
    }
//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        // 세션 관리자에 저장된 회원 정보 조회회
        Member loginMember = (Member)sessionManager.getSession(request);

        if(loginMember==null){
            return "home";
        }
        model.addAttribute("member",loginMember);
        return "loginHome";
    }
//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        // 로그인을 안한 처음 들어온 사람도 세션이 생길 수 있으니 false 로 생성
        HttpSession session = request.getSession(false);
        if(session == null){
            return "home";
        }
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(loginMember==null){
            return "home";
        }

        // 세션이 유지되면 로그인성공
        model.addAttribute("member",loginMember);
        return "loginHome";
    }
//    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) Member loginMember, Model model) {

        // 로그인을 안한 처음 들어온 사람도 세션이 생길 수 있으니 false 로 생성
        if(loginMember==null){
            return "home";
        }

        // 세션이 유지되면 로그인성공
        model.addAttribute("member",loginMember);
        return "loginHome";
    }
    @GetMapping("/")
    public String homeLoginV3ArgumentResolver
            (@Login Member loginMember, Model model) {

        // 로그인을 안한 처음 들어온 사람도 세션이 생길 수 있으니 false 로 생성
        if(loginMember==null){
            return "home";
        }

        // 세션이 유지되면 로그인성공
        model.addAttribute("member",loginMember);
        return "loginHome";
    }


}