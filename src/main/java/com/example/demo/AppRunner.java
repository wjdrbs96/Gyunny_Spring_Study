package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    // Bean의 정보 참조하기
    @Value("#{sample.data}")
    int myValue;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(myValue);   // 100
    }
}
