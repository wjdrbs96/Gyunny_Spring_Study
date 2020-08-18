package com.example.demo.controller;

import com.example.demo.dao.UserDaoService;
import com.example.demo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    // 사용자 전체조회
    @GetMapping("/users")
    public List<User> AllUsers() {
        return service.findALl();
    }

    @GetMapping("/users/{id}")
    public User findOne(@PathVariable int id) {
        return service.findOne(id);
    }

    @PostMapping("users")
    public void createUser(@RequestBody User user) {
        User save = service.save(user);
    }

}
