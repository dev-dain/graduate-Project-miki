package com.dayang.miki.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="basket_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="item_id")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id")
    private Item_option item_option;
    private int count;

    public static Basket createBasket(Item item, Item_option item_option, int cnt){
        Basket basket = new Basket();
        basket.setItem(item);
        if(item_option!=null) basket.setItem_option(item_option);
        basket.setCount(cnt);
        return basket;
    }
}
