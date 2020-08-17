package com.example.demo.controller;

import com.example.demo.model.DefaultRes;
import com.example.demo.model.LoginReq;
import com.example.demo.utils.ResponseMessage;
import com.example.demo.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class TestController {

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginReq loginReq) {
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, loginReq), HttpStatus.OK);
    }
}
