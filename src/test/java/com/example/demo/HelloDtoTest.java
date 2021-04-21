package com.example.demo;

import com.example.demo.dto.HelloDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by jg 2021/04/21
 */
public class HelloDtoTest {

    @Test
    public void dtoTest() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloDto helloDto = new HelloDto(name, amount);

        assertThat(helloDto.getName()).isEqualTo(name);
        assertThat(helloDto.getAmount()).isEqualTo(amount);
    }
}
