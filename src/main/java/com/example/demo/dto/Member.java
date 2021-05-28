package com.example.demo.dto;

import lombok.Getter;
import lombok.ToString;

/**
 * created by jg 2021/05/26
 */
@ToString
@Getter
public class Member {
    private Long id;
    private String name;
    private String part;
    private Long userIdx;

    public Member(String name, String part, Long userIdx) {
        this.name = name;
        this.part = part;
        this.userIdx = userIdx;
    }
}
