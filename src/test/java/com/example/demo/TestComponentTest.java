package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestComponentTest {

    @Test
    public void fieldInjectionTest() {
        TestComponent testComponent = new TestComponent();
        testComponent.createTest();
    }
}