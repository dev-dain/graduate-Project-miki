package com.dayang.miki.controller;

import com.dayang.miki.domain.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearStore {
    private Store store;
    private double km;
    private int stock;
}
