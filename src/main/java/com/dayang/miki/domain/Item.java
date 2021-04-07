package com.dayang.miki.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="item_id")
    private Long id;

    private String item_name;

    private String item_img;
    private int item_price;
    private int discount_price;
    private char is_optional;
    private double barcode;
    private char is_testable;
    private int stockQuantity;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="brand_id")
    private Brand brand;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     * STOCK 감소
     **/
    public void removeStock(int quantity){
        int retStock = this.stockQuantity - quantity;
        this.stockQuantity =retStock;
    }
    /**
     * STOCK 증가
     **/
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
}
