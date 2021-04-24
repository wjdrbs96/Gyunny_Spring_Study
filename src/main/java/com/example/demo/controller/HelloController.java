package com.example.demo.controller;

import com.example.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jg 2021/04/21
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;
}

