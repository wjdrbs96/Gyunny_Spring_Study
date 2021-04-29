package com.example.demo.dto;

import lombok.Builder;

/**
 * created by jg 2021/04/21
 */
public class HelloDto {
     private String name;
     private int amount;
     private int part;

     @Builder
     public HelloDto(String name, int amount) {
          this.name = name;
          this.amount = amount;
     }
}



