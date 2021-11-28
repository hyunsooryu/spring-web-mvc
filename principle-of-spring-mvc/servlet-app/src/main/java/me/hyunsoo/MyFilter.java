package me.hyunsoo;


import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("I AM MY FILTER");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MY FILTER INIT");
    }

    @Override
    public void destroy() {
        System.out.println("MY FILTER DESTROY");
    }
}
