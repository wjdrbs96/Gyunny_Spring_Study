package com.example.demo.utils.auth;

import com.example.demo.service.JwtService;
import com.example.demo.dto.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.DefaultRes;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
public class AuthAspect {

    private final static String AUTHORIZATION = "Authorization";
    /**
     * 실패 시 기본 반환 Response
     */
    private final static DefaultRes DEFAULT_RES = DefaultRes.builder().statusCode(401).responseMessage("인증 실패").build();
    private final static ResponseEntity<DefaultRes> RES_RESPONSE_ENTITY = new ResponseEntity<>(DEFAULT_RES, HttpStatus.UNAUTHORIZED);

    private final UserMapper userMapper;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;

    /**
     * Repository 의존성 주입
     */
    public AuthAspect(UserMapper userMapper, HttpServletRequest httpServletRequest, JwtService jwtService) {
        this.userMapper = userMapper;
        this.httpServletRequest = httpServletRequest;
        this.jwtService = jwtService;
    }

    @Around("@annotation(com.example.demo.utils.auth.Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final String jwt = httpServletRequest.getHeader(AUTHORIZATION);

        // 토큰 존재 여부 확인
        if (jwt == null) {
            return RES_RESPONSE_ENTITY;
        }

        // 토큰 해독
        final JwtService.TOKEN token = jwtService.decode(jwt);

        // 토큰 검사
        if (token == null) {
            return RES_RESPONSE_ENTITY;
        } else {
            final User user = userMapper.findByUserIdx(token.getUserIdx());
            // 유효 사용자 검사
            if (user == null) {
                return RES_RESPONSE_ENTITY;
            }
            return pjp.proceed(pjp.getArgs());
        }
    }

}
