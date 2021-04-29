package com.example.demo.controller;

import com.example.demo.dto.HelloDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jg 2021/04/21
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("/")
    public HelloDto hello() {
        return HelloDto.builder()
                .amount(100)
                .name("Gyunny")
                .build();
    }
}

