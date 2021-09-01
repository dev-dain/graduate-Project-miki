package com.dayang.miki.review;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
public class ReviewDTO {
    private Long reviewId;
    private String content;
    private double rate;
    private String image;
    private LocalDateTime date;
}
