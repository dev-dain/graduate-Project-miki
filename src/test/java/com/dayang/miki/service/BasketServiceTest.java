package com.dayang.miki.service;

import com.dayang.miki.repository.BasketRepository;
import org.assertj.core.api.Assertions;
import com.dayang.miki.domain.Basket;
import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_option;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@Transactional
class BasketServiceTest {

    @Autowired
    BasketService basketService;
    @Autowired
    ItemService itemService;
    @Autowired
    BasketRepository basketRepository;
    @Test
    @Rollback(value = false)
    void save(){
        //when
        Item item = itemService.findOne(5L);
        Item_option item_option = itemService.Single_Item_option("더랩바이블랑두 올리고 히알루론산 토너+로션 2종 기획 - 더랩바이블랑두 올리고 히알루론산 토너+로션 2종 기획");
        //given
        Basket basket = Basket.createBasket(item, item_option, 5);

        basketService.validate(basket);
        //then

    }
    @Test
    void findAll(){
        List<Basket> basketList = basketService.findAll();
        for(Basket basket : basketList){
            System.out.println(basket.getId() + " " + basket.getItem().getName());
        }
    }

    @Test
    void deleteOne(){
        basketService.deleteOne(1L);
    }
    @Test
    @Rollback(value = false)
    void deleteAll(){
        basketService.truncateBasket();
    }
}