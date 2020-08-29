package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostModel {
    private String authors;
    private String title;
    private String createdAt;
}
