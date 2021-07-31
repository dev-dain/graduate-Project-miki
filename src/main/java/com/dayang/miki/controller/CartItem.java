package com.dayang.miki.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private Long item_id;
    private Long item_option_id;
    private int count;
}
