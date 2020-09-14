package com.example.demo.model;

import lombok.Data;

@Data
public class ProfileModel {
    private int profileIdx;
    private String profileName;
    // 프로필 사진 객체
    private String profileURL;
}
