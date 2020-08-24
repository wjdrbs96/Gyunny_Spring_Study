package com.example.demo.service;

import com.example.demo.dto.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.DefaultRes;
import com.example.demo.model.Users;
import com.example.demo.utils.ResponseMessage;
import com.example.demo.utils.StatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 멤버 전체 조회
    public DefaultRes getAllUsers() {
        final List<Users> userList = userMapper.findALl();
        if (userList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, userList);
    }

    // 멤버 한명 조회
   public DefaultRes findById(final int id) {
        final User user = userMapper.findByUserIdx(id);
        if (user == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, user);
    }

}
