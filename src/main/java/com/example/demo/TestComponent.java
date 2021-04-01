package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestComponent {

    @Autowired
    private TestRepository testRepository;

    public void createTest() {
        Test test = testRepository.findByTest(1);

    }
}
