package com.example.demo.controller;

import com.example.demo.service.PostService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private PostService postService;

    /**
     * PostService 생성자 의존성 주입
     *
     * @param PostService
     */
    public PostController(PostService postService) {
        this.postService = postService;
    }



}
