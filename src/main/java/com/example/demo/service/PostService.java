package com.example.demo.service;

import com.example.demo.mapper.PostMapper;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private PostMapper postMapper;

    // 생성자 의존성 주입
    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }


}
