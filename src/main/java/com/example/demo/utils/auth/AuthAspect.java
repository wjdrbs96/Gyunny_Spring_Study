package com.example.demo.utils.auth;

import com.example.demo.Service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
public class AuthAspect {

    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;

    public AuthAspect(HttpServletRequest httpServletRequest, JwtService jwtService) {
        this.httpServletRequest = httpServletRequest;
        this.jwtService = jwtService;
    }
}
