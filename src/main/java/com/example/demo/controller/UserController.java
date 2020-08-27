package com.example.demo.controller;

import com.example.demo.model.DefaultRes;
import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    private UserService userService;

    // Service 생성자 의존성 주입
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 전체조회
    @GetMapping("/users")
    public ResponseEntity AllUsers() {
        try {
            return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 유저 한명 조회
    @GetMapping("/users/{id}")
    public ResponseEntity userOne(@PathVariable("id") int id) {
        try {
            return new ResponseEntity(userService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 유저 등록
    @PostMapping("/users")
    public ResponseEntity userAdd(@RequestBody User user) {
        try {
            return new ResponseEntity(userService.userAdd(user), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 유저 정보 수정
    @PutMapping("users/{id}")
    public ResponseEntity userUpdate(@PathVariable int id,
                                     @RequestBody User user) {
        try {
            return new ResponseEntity(userService.userUpdate(id, user), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 유저 삭제
    @DeleteMapping("users/{id}")
    public ResponseEntity userDelete(@PathVariable int id) {
        try {
            return new ResponseEntity(userService.userDelete(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }
}
