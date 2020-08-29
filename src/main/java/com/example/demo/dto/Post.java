package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Post {
    private int postId;
    private String title;
    private String authors;
    private Date date;
    private int memberIdx;
}
