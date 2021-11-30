package me.hyunsoo;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 *
 *
 * web.xml을 탈피하여, 자바 코드로 application을 띄우는 방법
 */
//WebApplicationInitializer
public class WebApplication implements WebApplicationInitializer{

    //@Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        //Root Config
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));

        //WebConfig - DispatcherServlet WebConfig
        AnnotationConfigWebApplicationContext context  = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/app/*");
    }
}
