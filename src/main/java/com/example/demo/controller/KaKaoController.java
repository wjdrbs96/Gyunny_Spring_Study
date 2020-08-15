package com.example.demo.controller;

import com.example.demo.service.KaKaoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KaKaoController {

    @Autowired
    KaKaoAPI kaKaoAPI;

    @GetMapping("/")
    public String index() {
        return "test";
    }

    @GetMapping(value = "/login")
    public String login(@RequestParam("code") String code) {
        String access_Token = kaKaoAPI.getAccessToken(code);
        System.out.println("Controller Access Token: " + access_Token);
        return "test";
    }
}
