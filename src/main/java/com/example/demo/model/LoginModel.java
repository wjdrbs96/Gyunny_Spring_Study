package com.example.demo.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginModel {
    @NotNull
    private String id;
    @NotNull
    private String password;
}
