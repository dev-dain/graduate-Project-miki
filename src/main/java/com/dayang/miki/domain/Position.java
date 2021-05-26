package com.dayang.miki.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
public class Position {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private double latitude;
    private double  longitude;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="store_id")
    private Store store;
}
