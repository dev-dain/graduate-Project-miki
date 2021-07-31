package com.dayang.miki.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Item_option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="item_option_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private String item_option_name;
    private int stockQuantity;
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