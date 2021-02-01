package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Sample {
    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
