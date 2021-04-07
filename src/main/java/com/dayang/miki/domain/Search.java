package com.dayang.miki.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Search {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name ="search_id")
    private Long id;
    private String content;
}
