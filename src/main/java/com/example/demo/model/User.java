package com.example.demo.model;

import lombok.Data;

@Data
public class User {
    private int userIdx;
    private String name;
    private String part;
    private int age;
    private String email;


   public static class Builder {
       private int userIdx = 1;
       private String name = "이름";
       private String part = "서버";
       private int age = 25;
       private String email = "Builder@naver.com";

       public Builder(String name, String part) {
           this.name = name;
           this.part = part;
       }

       public Builder userIdx(int userIdx) {
           this.userIdx = userIdx;
           return this;
       }
       public Builder age(int age) {
           this.age = age;
           return this;
       }

       public Builder email(String email) {
           this.email = email;
           return this;
       }

       public User build() {
           return new User(this);
       }
   }

   public User(Builder builder) {
       this.userIdx = builder.userIdx;
       this.name = builder.name;
       this.part = builder.part;
       this.age = builder.age;
       this.email = builder.email;
   }

    public static void main(String[] args) {
        User user = new Builder("이름", "파트")
                .userIdx(1)
                .age(25)
                .email("Builder@naver.com")
                .build();
    }
}
