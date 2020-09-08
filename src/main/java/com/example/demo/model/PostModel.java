package com.example.demo.model;

import lombok.Data;

@Data
public class PostModel {
    private int postIdx;
    private String authors;
    private String title;
    private String createdAt;
}
