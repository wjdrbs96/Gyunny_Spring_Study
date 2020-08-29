package com.example.demo.model;

import lombok.Data;

import java.util.Date;

@Data
public class PostModel {
    private String author;
    private String title;
    private Date date;
}
