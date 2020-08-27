package com.example.demo.controller;

import com.example.demo.dto.Member;
import com.example.demo.model.DefaultRes;
import com.example.demo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("member")
public class MemberController {

    MemberService memberService;

    // Service 생성자 의존성 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("signUp")
    public ResponseEntity signUp(@RequestBody Member member) {
        try {
            return new ResponseEntity(memberService.signUp(member), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("signIn")
    public ResponseEntity siginIn(@RequestBody Member member) {
        try {
            return new ResponseEntity(memberService.signIn(member), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
