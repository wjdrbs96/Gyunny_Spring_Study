package com.example.demo.controller;

import com.example.demo.dao.UserDaoService;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserDaoService service;
    private UserService userService;

    public UserController(UserDaoService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

//    // 사용자 전체조회
//    @GetMapping("/users")
//    public List<User> AllUsers() {
//        return service.findALl();
//    }
//
//    @GetMapping("/users/{id}")
//    public User findOne(@PathVariable int id) {
//        return service.findOne(id);
//    }
//
//    @PostMapping("users")
//    public void createUser(@RequestBody User user) {
//        User save = service.save(user);
//    }

    @GetMapping("/users")
    public ResponseEntity AllUsers() {
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity userOne(@PathVariable("id") int id) {
        return new ResponseEntity(userService.findById(id), HttpStatus.OK);
    }
}
