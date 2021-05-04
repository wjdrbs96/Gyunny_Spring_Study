package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * created by jg 2021/05/04
 */
@Getter
@NoArgsConstructor
public class GroupDto {
    private int groupId;
    private String title;

    public GroupDto(String title) {
        this.title = title;
    }
}
