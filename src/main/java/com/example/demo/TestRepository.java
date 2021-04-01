package com.example.demo;

import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {

    public Test findByTest(int testId) {
        return new Test("Gyunny", "Korea");
    }
}
