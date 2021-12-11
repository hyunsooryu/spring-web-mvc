package me.hyunsoo.handlermethod.controller;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Locale;

public class VisitTimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         HttpSession session = request.getSession();
         if(session.getAttribute("visitTime") == null){
             LocalDateTime dateTime = LocalDateTime.now();
             System.out.println("방문시각 로그 : " + dateTime);
             session.setAttribute("visitTime", dateTime);
         }
         return true;
    }
}
