package com.example.demo.controller;

import com.example.demo.dto.Member;
import com.example.demo.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * created by jg 2021/04/21
 */
@RequiredArgsConstructor
@RestController
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/")
    public List<Member> gyun() {
        return helloService.helloService();
    }
}