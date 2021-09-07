package com.dayang.miki.review;

import com.dayang.miki.Item.DevItemService;
import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Review_img;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DevReviewServiceTest {

    @Autowired
    DevReviewService devReviewService;
    @Autowired
    DevItemService devItemService;

    @Test
    void itemReview() {
        //given
        Long itemId = 324L;

        //when
        List<ReviewDTO> reviewDTOList = devReviewService.itemReview(itemId);

        //then
        assertThat(reviewDTOList.size()).isEqualTo(10);
        assertThat(reviewDTOList.get(0).getImage()).isEqualTo("http://www.bbia.co.kr/shopimages/bbia/0070010000623.jpg?1605499285");
        assertThat(reviewDTOList.get(1).getImage()).isNull();
    }

    @Test
    void reviewCnt(){
        //given
        Long itemId = 324L;
        Map<String, Double> map1 = new HashMap<>();
        Map<String, Double> map2 = new HashMap<>();
        //when
        map1 = devReviewService.reviewSizeRate(itemId); // 리뷰가 있는 아이템
        map2 = devReviewService.reviewSizeRate(1L); //리뷰가 없는 아이템
        //then
        assertThat(map1.get("size")).isEqualTo(10.0);
        assertThat(map1.get("rate")).isEqualTo(3.25);

        assertThat(map2.get("size")).isEqualTo(0.0);
        assertThat(map2.get("rate")).isNaN();
    }
}