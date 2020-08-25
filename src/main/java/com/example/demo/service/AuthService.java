package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

//    private final UserMapper userMapper;
//    private final JwtService jwtService;
//
//    public AuthService(UserMapper userMapper, JwtService jwtService) {
//        this.userMapper = userMapper;
//        this.jwtService = jwtService;
//    }
//
//    // Client 로그인 요청이 왔을 때
//    public DefaultRes<JwtService.TokenRes> login(final LoginReq loginReq) {
//        final User user = userMapper.findByNameAndPassword(loginReq.getName(), loginReq.getPassword());
//        if (user != null) {
//            // 토큰 생성
//            final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(user.getUserIdx()));
//            return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
//        }
//
//        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
//    }
}
