package com.dayang.miki.service;


import com.dayang.miki.domain.*;
import com.dayang.miki.repository.CartRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@Transactional
class BasketServiceTest {

    @Autowired
    CartService cartService;
    @Autowired
    ItemService itemService;
    @Autowired
    CartRepository cartRepository;
    @Test
    @Rollback(value = false)
    void save(){
        //when
        Item item = itemService.findOne(5L);
        Item_option item_option = itemService.Single_Item_option("더랩바이블랑두 올리고 히알루론산 토너+로션 2종 기획 - 더랩바이블랑두 올리고 히알루론산 토너+로션 2종 기획");
        //given
        Cart cart = Cart.createCart(item, item_option, 5);

        cartService.validate(cart);
        //then

    }
/*    @Test
    void findAll(){
        List<Cart> CartList = cartService.findAll();
        for(Cart cart : CartList){
            System.out.println(cart.getId() + " " + cart.getItem().getName());
        }
    }*/

/*    @Test
    void cartList(){
        List<Cart> carts= cartService.findAll();
        List<Item> items = new ArrayList<>();
        List<Item_option> item_options = new ArrayList<>();
        List<Item_img> imgs = new ArrayList<>();
        for(Cart cart : carts){
            imgs.add(itemService.itemImg(cart.getItem().getId()));
            items.add(cart.getItem());
            item_options.add(cart.getItem_option());
        }
        for(Item item : items){
            System.out.println(item.getId());
        }
        for(Item_option item_option : item_options){
            System.out.println(item_option.getId());
        }
    }*/

    @Autowired
    TestService testService;
    @Test
    void ddd(){
        Item item = itemService.findOne(324L);
        List<TestColor> testColors = testService.findByItem(item);
        List<Item_option> item_options = itemService.itemOptionList(item);
        for(Item_option item_option : item_options){
            System.out.println(item_option.getId());
        }
    }
    @Test
    void deleteOne(){
        cartService.deleteOne(1L);
    }
    @Test
    @Rollback(value = false)
    void deleteAll(){
        cartService.truncateCart();
    }


    @Test
    void testCart(){
        int pageNum = 1;
        int[] arr = {};
        arr[0] = 1;
        List<Cart> carts= cartService.findAll();
        List<Item> items = cartService.getItem();
        List<Item_option> item_options = cartService.getItemOption();

        List<Item_img> imgs = itemService.getCartImg(items);
        System.out.println("----------------------------------");

        for(Cart cart : carts){
            System.out.println(cart.getItem().getId());
        }
        System.out.println("----------------------------------");
        for(Item item : items){
            System.out.println(item.getId());
        }
        System.out.println("----------------------------------");

        for(Item_option item_option : item_options){
            System.out.println(item_option.getItem().getId());
        }
        System.out.println("----------------------------------");
        for(Item_img item_img : imgs){
            System.out.println(item_img.getItem().getId());
        }
        System.out.println("----------------------------------");
    }
}