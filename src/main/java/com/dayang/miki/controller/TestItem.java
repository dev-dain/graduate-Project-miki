package com.dayang.miki.controller;

import lombok.RequiredArgsConstructor;

public class TestItem {
    private Long itemId;

    private String itemName;
    private String itemImage;
    private Long itemOptionId;
    private String itemOptionName;
    private String color;
    private double alpah;
    private String testLocation;
    public TestItem(Long itemId, String itemName, String itemImage, Long itemOptionId, String itemOptionName, String color, double alpah, String testLocation) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemOptionId = itemOptionId;
        this.itemOptionName = itemOptionName;
        this.color = color;
        this.alpah = alpah;
        this.testLocation = testLocation;
    }

}
