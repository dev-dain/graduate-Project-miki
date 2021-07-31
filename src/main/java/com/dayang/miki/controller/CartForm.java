package com.dayang.miki.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartForm {
    private Long id;
    private Long item_id;
    private String item_option;
    private int count;
}
