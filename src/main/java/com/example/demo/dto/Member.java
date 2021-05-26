package com.example.demo.dto;

import lombok.Getter;

/**
 * created by jg 2021/05/26
 */
@Getter
public class Member {
    private Long id;
    private String name;
    private String part;

    public Member(String name, String part) {
        this.name = name;
        this.part = part;
    }
}
