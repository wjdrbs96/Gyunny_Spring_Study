package com.example.demo.dto;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * created by jg 2021/05/05
 */
@Component
public class FilterTest2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init2");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter2");
    }

    @Override
    public void destroy() {
        System.out.println("Destroy2");
    }
}
