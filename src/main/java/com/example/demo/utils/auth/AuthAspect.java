package com.example.demo.utils.auth;

import com.example.demo.dto.Member;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.DefaultRes;
import com.example.demo.service.JwtService;
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

    private final MemberMapper memberMapper;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;

    /**
     * Repository 의존성 주입
     */
    public AuthAspect(MemberMapper memberMapper, HttpServletRequest httpServletRequest, JwtService jwtService) {
        this.memberMapper = memberMapper;
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
            final Member member = memberMapper.findByMemberIdx(token.getMemberIdx());
            // 유효 사용자 검사
            if (member == null) {
                return RES_RESPONSE_ENTITY;
            }
            return pjp.proceed(pjp.getArgs());
        }
    }

}
