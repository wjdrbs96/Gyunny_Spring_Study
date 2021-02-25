package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class TestComponent {

    @Bean(initMethod = "init", destroyMethod = "close")
    public void test() {

    }

    @PostConstruct
    public void init() {
        System.out.println("초기화");
    }

    @PreDestroy
    public void close() {
        System.out.println("종료");
    }
}
