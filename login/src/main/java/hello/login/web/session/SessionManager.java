package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리
 */
@Component
public class SessionManager {

    private static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String,Object> sessionStore = new ConcurrentHashMap<>();

    /**
     *  세션 생성
     */
    public void createSession(Object value, HttpServletResponse response){
        //세션 ID 생성 (추적 불가느한 임의으이 값)
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        // 쿠키 생성
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);

        response.addCookie(cookie);
    }

    /**
     *  세션 조회
     */
    public Object getSession(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if(sessionCookie == null){
            return null;
        }else {
            return sessionStore.get(sessionCookie.getValue());
        }

    }

    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request,HttpServletResponse response){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
        if(sessionCookie != null){
            sessionStore.remove(sessionCookie.getValue());
        }
    }




    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

}
