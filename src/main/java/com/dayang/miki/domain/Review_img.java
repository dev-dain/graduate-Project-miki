package com.dayang.miki.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Review_img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_img_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="review_id")
    private Review review;
    private String review_img;
}
