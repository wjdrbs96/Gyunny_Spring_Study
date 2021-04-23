package com.example.demo.dto;

/**
 * created by jg 2021/04/24
 */
public class Order {
    private Long memberId;
    private String item;
    private int itemPrice;
    private int discountPrice;

    public Order(Long memberId, String item, int itemPrice, int discountPrice) {
        this.memberId = memberId;
        this.item = item;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
    }
}
