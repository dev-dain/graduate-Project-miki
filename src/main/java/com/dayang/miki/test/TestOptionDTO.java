package com.dayang.miki.test;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TestOptionDTO {
    private Long optionId;
    private String optionName;
    private Long optionColorId;
    private String optionColor;
    private double optionColorAlpha;
    private String optionFaceLocation;
}
