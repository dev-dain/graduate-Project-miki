package com.dayang.miki.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Brand {

    @Id
    @GeneratedValue
    @Column(name ="brand_id")
    private Long id;
    private String brand_name;
    private String brand_img;
}