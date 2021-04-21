package com.example.demo.controller;

import com.example.demo.dto.HelloDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jg 2021/04/21
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String test() {
        return "test";
    }

    @GetMapping("/hello/dto")
    public HelloDto hellDto(@RequestParam("name") String name,
                            @RequestParam("amount") int amount) {
        return new HelloDto(name, amount);
    }
}
