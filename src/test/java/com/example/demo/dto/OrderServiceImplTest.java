package com.example.demo.dto;

import org.junit.jupiter.api.Test;

/**
 * created by jg 2021/04/24
 */
class OrderServiceImplTest {

    @Test
    void createOrder() {
        OrderServiceImpl orderService = new OrderServiceImpl(new MemberRepository(), new DiscountPolicy());
        orderService.createOrder(1L, "item", 1000);
    }
}