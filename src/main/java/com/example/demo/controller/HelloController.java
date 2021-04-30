package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * created by jg 2021/04/21
 */
@Slf4j
@RestController
public class HelloController {

    @@GetMapping("/")
    public String test(@RequestPart(value = "profileImg") MultipartFile multipartFile,) {
        return "test";
    }
}

