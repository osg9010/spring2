package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/members/add", "/login", "/logout","/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작{}",uri);
            if(isLoginCheckPath(uri)){
                log.info("인증 체크 로직 실행{}",uri);
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                    log.info("미인증 사용자 요청 {}",uri);
                    // 로그인으로 Redirect
                    httpResponse.sendRedirect("/login?redirectURL="+uri);
                    return;
                }
            }
            chain.doFilter(request,response);
        }catch (Exception e){
            // 예외로깅 가능하지만, 톰캣까지 예외를 보내주어야 함
            throw e;
        }finally {
            log.info("인증 체크 필터 종료{}",uri);
        }

    }

    /**
     *  화이트 리스트의 경우 인증체크 x
     */
    private boolean isLoginCheckPath(String uri){
        return !PatternMatchUtils.simpleMatch(whiteList,uri);
    }


}
