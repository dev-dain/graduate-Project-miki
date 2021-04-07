package com.dayang.miki.service;

import com.dayang.miki.domain.Category;
import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_option;
import com.dayang.miki.domain.Product_img;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class ItemServiceTest {
    @Autowired
    ItemService itemService;
    @Autowired
    EntityManager em;
    @Test
    void categoryItemList(){

        //given
        Long category_id = 36L;
        //when
        List<Item> items = itemService.categoryItemList(category_id);
        for(Item item : items){
            System.out.println("item = " +item.getItem_name());
        }
        //then
    }

    @Test
    void option_List(){

        //given
        Long item_id = 1L;
        Item item = itemService.findOne(item_id);
        //when
        List<Item_option> item_option = itemService.itemOptionList(item);
        for(Item_option item_options : item_option){
            System.out.println("item_option = " +item_options.getItem_option_name());
        }
        //then
    }

    @Test
    void Product_Img_List(){

        //given
        Long item_id = 4L;
        Item item = itemService.findOne(item_id);
        //when
        List<Product_img> product_imgs = itemService.productImgs(item);
        for(Product_img product_img : product_imgs){
            System.out.println("product_img = " +product_img.getProduct_img());
        }
        //then
    }
}