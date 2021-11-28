package me.hyunsoo;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ByeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ApplicationContext applicationContext = (ApplicationContext) getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        HelloService helloService = applicationContext.getBean(HelloService.class);

        String name = helloService.getName();

        resp.getWriter().println("<html><head></head><body>");
        resp.getWriter().println("<h2> BYE BYE, " + name + "</h2>");
        resp.getWriter().println("</body></html>");
    }


    private String getName(){
        System.out.println("I AM BYE SERVLET");
        System.out.println(getServletContext());
        return (String)getServletContext().getAttribute("name");
    }
}
