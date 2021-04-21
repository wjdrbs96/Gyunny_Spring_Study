package com.example.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * created by jg 2021/04/21
 */
@Getter
@RequiredArgsConstructor
public class HelloDto {
    private final String name;
    private final int amount;
}
