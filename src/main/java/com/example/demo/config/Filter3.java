package com.example.demo.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * created by jg 2021/05/04
 */
@Component
public class Filter3 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init3");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter3");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("destroy3");
    }
}
