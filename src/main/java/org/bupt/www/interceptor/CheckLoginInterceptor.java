package org.bupt.www.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        if (!url.contains("login") && !url.contains("register")) {
            HttpSession session = request.getSession();
            if (session.getAttribute("identity") == null) {
                response.sendRedirect("/user/login");
                return false;
            }
        }
        return true;
    }
}
