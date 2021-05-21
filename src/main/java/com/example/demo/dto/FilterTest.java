package com.example.demo.dto;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * created by jg 2021/05/05
 */
@Component
public class FilterTest implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("DO Filter");
        chain.doFilter(request, response);
        // CORS, XSS , 인코딩
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
