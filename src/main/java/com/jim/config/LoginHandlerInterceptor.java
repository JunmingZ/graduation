package com.jim.config;

import com.jim.dto.LoginDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginDTO login = (LoginDTO)request.getSession().getAttribute("loginDTO");
        System.out.println("拦截器中的LoginDTO："+login);
        if(login==null){
            response.sendRedirect("/login");
            return false;
        }else {
            return true;
        }

    }

}
