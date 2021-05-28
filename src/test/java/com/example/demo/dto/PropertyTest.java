package com.example.demo.dto;

import com.example.demo.config.FruitProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * created by jg 2021/05/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyTest {

    @Autowired
    FruitProperty fruitProperty;

    @Test
    public void test() {
        System.out.println(fruitProperty.getName());
    }
}
