package com.dayang.miki.test;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class TestItemDTO {
    private Long itemId;
    private String itemName;
    private String itemImage;
    private List<TestOptionDTO> testOption;
}
