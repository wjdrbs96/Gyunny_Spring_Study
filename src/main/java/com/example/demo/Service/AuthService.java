package com.example.demo.Service;

import com.example.demo.dto.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.DefaultRes;
import com.example.demo.model.LoginReq;
import com.example.demo.utils.ResponseMessage;
import com.example.demo.utils.StatusCode;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final JwtService jwtService;

    public AuthService(UserMapper userMapper, JwtService jwtService) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    public DefaultRes<JwtService.TokenRes> login(final LoginReq loginReq) {
        final User user = userMapper.findByNameAndPassword(loginReq.getName(), loginReq.getPassword());
        if (user != null) {
            // 토큰 생성
            final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(user.getUserIdx()));
            return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
        }

        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
    }
}
