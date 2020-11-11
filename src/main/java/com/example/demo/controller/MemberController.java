package com.example.demo.controller;

import com.example.demo.model.DefaultRes;
import com.example.demo.model.LoginModel;
import com.example.demo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("member")
public class MemberController {

    private MemberService memberService;

    /**
     * MemberService 생성자 의존성 주입
     *
     * @param MemberService
     */
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입
    @PostMapping("signUp")
    public ResponseEntity signUp(@RequestBody @Valid LoginModel loginModel) {
        try {
            return new ResponseEntity(memberService.signUp(loginModel), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 로그인
    @PostMapping("signIn")
    public ResponseEntity siginIn(@Valid @RequestBody LoginModel loginModel) {
        try {
            log.info(loginModel.toString());
            return new ResponseEntity(memberService.signIn(loginModel), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
