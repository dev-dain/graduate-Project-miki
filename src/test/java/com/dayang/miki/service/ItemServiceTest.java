package com.dayang.miki.service;

import com.dayang.miki.domain.*;
import com.dayang.miki.repository.BrandRepository;
import com.dayang.miki.repository.CategoryRepository;
import com.dayang.miki.repository.ItemLogicRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

@SpringBootTest
@Transactional
class ItemServiceTest {
    @Autowired
    ItemService itemService;
    @Autowired
    EntityManager em;
    @Autowired
    ItemLogicRepository itemLogicRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void categoryItemList(){

        //given
        Long category_id = 36L;
        //when
        List<Item> items = itemService.categoryItemList(category_id);
        for(Item item : items){
            System.out.println("item = " +item.getName());
        }
        //then
    }
    @Test
    void findByName(){

        //given
        String name = "ddd";
        //when
        List<Item> items = itemService.findByItemName(name);
        //then
        for(Item item : items){
            System.out.println(item.getName());
        }

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

    @Test
    void ReviewCnt(){
        //given
        Long item_id = 1L;
        Item item = itemService.findOne(item_id);
        List<Review> reviews = itemService.reviews(item);
        //when
        int cnt = itemService.reviewCnt(item);
        //then
        Assertions.assertThat(cnt).isEqualTo(reviews.size());
    }

    @Test
    void ReviewRate(){
        //given
        Long item_id = 1L;
        Item item = itemService.findOne(item_id);
        List<Review> reviews = itemService.reviews(item);
        //when
        float rate = itemService.reviewRate(item);
        //then

        Assertions.assertThat(rate).isEqualTo(3.1f);
    }

    @Test
    void Review(){
        //given
        Long item_id = 1L;
        Item item = itemService.findOne(item_id);
        List<Review> reviews = itemService.reviews(item);
        //when
        for(Review review : reviews){
            System.out.println(review.getCreated_at().getYear()+" "+review.getCreated_at().getMonth()+" "+review.getCreated_at().getDayOfWeek()+ "    " +review.getReview_content());
        }
    }

    @Test
    void ReviewImg(){
        //given
        Long item_id=1L;
        Item item = itemService.findOne(item_id);

        //when
        List<Review_img> ri = itemService.reviewImgs(item);
        for(Review_img review_img : ri){
            System.out.println(review_img.getReview_img() + " " + review_img.getId());
        }
    }

    @Test
    void findCategoryByName(){
        String name = "로션";
        List<Category> id = categoryRepository.findByNameContaining(name);
        for(Category i : id){
            System.out.println(i.getId());
        }

    }
    @Test
    void findItemByBrandName(){
        String name = "ㄷㄱ";
        List<Item> items = itemService.findItemByBrandName(name);
        for(Item item: items){
            System.out.println(item.getName());
        }
    }
    @Test
    void findBrandByName(){
        String name = "ㄷㄱ";
        Brand brand = itemService.findByBrandName(name);

        System.out.println(brand.getId());
    }
}