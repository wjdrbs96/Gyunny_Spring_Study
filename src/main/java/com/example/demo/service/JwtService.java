package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.auth0.jwt.JWT.require;

@Slf4j
@Service
public class JwtService {

    @Value("${JWT.ISSUER}")
    private String ISSUER;

    @Value("${JWT.SECRET}")
    private String SECRET;

     /**
     * 토큰 생성
     *
     * @param memberIdx 토큰에 담길 로그인한 사용자의 회원 고유 IDX
     * @return 토큰
     */

    public String create(final int memberIdx) {
        try {
            JWTCreator.Builder b = JWT.create();
            b.withIssuer(ISSUER);
            b.withClaim("memberIdx", memberIdx);
            return b.sign(Algorithm.HMAC256(SECRET));
        } catch (JWTCreationException jwtCreationException) {
            log.info(jwtCreationException.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 토큰 해독
     *
     * @param token 토큰
     * @return 로그인한 사용자의 회원 고유 IDX
     */

    public TOKEN decode(final String token) {
        try {
            // 토큰 해독 객체 생성
            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build();
            // 토큰 검증
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            // 토큰 payload 반환, 정상적인 토큰이라면 토큰 사용자 고유 ID
            return new TOKEN(decodedJWT.getClaim("memberIdx").asLong().intValue());
        } catch (JWTVerificationException jve) {
            log.error(jve.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 정상적인 토큰이 아니라면 -1로 반환
        return new TOKEN();
    }

    public static class TOKEN {
        //토큰에 담길 정보 필드
        //초기값을 -1로 설정함으로써 로그인 실패시 -1반환
        private int memberIdx = -1;

        public TOKEN() {

        }

        public TOKEN(final int userIdx) {
            this.memberIdx = userIdx;
        }

        public int getMemberIdx() {
            return memberIdx;
        }
    }

    public static class TokenRes {
        private String token;

        public TokenRes() {

        }

        public TokenRes(final String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
