package com.dayang.miki.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Item_img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="item_img_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private String item_img;

}
