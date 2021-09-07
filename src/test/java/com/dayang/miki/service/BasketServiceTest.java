package com.dayang.miki.service;


import com.dayang.miki.category.DevCategoryService;
import com.dayang.miki.domain.*;
import com.dayang.miki.repository.CartReposit;
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
    CartService cartService;
    @Autowired
    ItemService itemService;
    @Autowired
    CartReposit cartRepository;


    @Test
    @Rollback(value = false)
    void save(){
        java.util.Date time = new java.util.Date(System.currentTimeMillis());
        String date = "2021-";
        int month = time.getMonth();
        date +=Integer.toString(month);
        date +="-01";
        List<Item> BestItems = itemService.newItem(date);
        List<Item_img> BestItemImg = new ArrayList<>();
        for(Item item : BestItems){
            BestItemImg.add(itemService.itemImg(item.getId()));
        }

        List<Item> mdsPickItem = itemService.recommendItem();
        List<Item_img> mdsPickItemImg = new ArrayList<>();
        for(Item item : mdsPickItem){
            mdsPickItemImg.add(itemService.itemImg(item.getId()));
        }


        System.out.println(BestItems.get(0).getId());
        System.out.println(mdsPickItem.get(0).getId());

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