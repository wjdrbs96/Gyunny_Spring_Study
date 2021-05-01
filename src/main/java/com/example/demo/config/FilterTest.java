package com.example.demo.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * created by jg 2021/04/30
 */
@Component
public class FilterTest implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("==");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
