package com.example.demo.dto;

import lombok.Data;

@Data
public class Post {
    private int postIdx;
    private String authors;
    private String title;
    private String createdAt;
    private int memberIdx;
}
