package com.example.demo.controller;

import com.example.demo.dto.HelloDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jg 2021/04/21
 */
@RestController
public class HelloController {

    @PostMapping("/")
    public HelloDto test(@RequestBody HelloDto helloDto) {
        System.out.println(helloDto);
        return helloDto;
    }

    @GetMapping("/gyun")
    public String gyun() {
        return "Gyunny";
    }
}