package com.example.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * created by jg 2021/05/28
 */
@Setter
@Getter
@Component
@ConfigurationProperties("fruit")
public class FruitProperty {
    private String name;
}


