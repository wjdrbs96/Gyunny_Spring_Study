package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Member {
    private int memberIdx;
    private String id;
    private String password;

    public Member(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
