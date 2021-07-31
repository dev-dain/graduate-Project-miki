package com.dayang.miki.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="cart_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="item_id")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id")
    private Item_option item_option;
    private int count;

    public static Cart createCart(Item item, Item_option item_option, int cnt){
        Cart cart = new Cart();
        cart.setItem(item);
        if(item_option!=null) cart.setItem_option(item_option);
        cart.setCount(cnt);
        return cart;
    }
}
