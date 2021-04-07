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
    private int count;
}
