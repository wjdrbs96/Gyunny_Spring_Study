package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

//    // 실패시 사용
//    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
//
//    private final AuthService authService;
//
//    public LoginController(AuthService authService) {
//        this.authService = authService;
//    }
//
//    /**
//     * 로그인
//     *
//     * @param loginReq 로그인 객체
//     * @return ResponseEntity
//     */
//    @PostMapping("login2")
//    public ResponseEntity login(@RequestBody LoginReq loginReq) {
//        try {
//            return new ResponseEntity(authService.login(loginReq), HttpStatus.OK);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
