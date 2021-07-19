package com.dayang.miki.controller;

import com.dayang.miki.domain.*;
import com.dayang.miki.service.CartService;
import com.dayang.miki.service.ItemService;
import com.dayang.miki.service.StoreService;
import com.dayang.miki.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final ItemService itemService;
    private final CartService cartService;

    @GetMapping("/test")
    public String test(){
        return "test/test-start";
    }


    @GetMapping("/test/{item_id}")
    public String ItemOption(@PathVariable("item_id") Long id, Model model){
        Item item = itemService.findOne(id);
        Item_img item_img = itemService.itemImg(item.getId());
        List<Item_option> item_options = itemService.itemOptionList(item);
        List<TestColor> testColors = testService.findByItem(item);

        model.addAttribute("item_img", item_img);
        model.addAttribute("item", item);
        model.addAttribute("item_options", item_options);
        model.addAttribute("testColor", testColors);
        return "test/test-main";
    }



    @GetMapping("/testAll")
    public String testItems( Model model){
        List<Cart> carts = cartService.findAll();
        List<Item> items = new ArrayList<>();

        List<Item_img> item_imgs = new ArrayList<>();

        List<Item_option> item_options = new ArrayList<>();
        List<Item_option> tmpItemOption = new ArrayList<>();

        List<TestColor> testColors = new ArrayList<>();
        List<TestColor> tmpTestColor = new ArrayList<>();

        for(Cart c : carts){
            Item item = itemService.findOne(c.getItem().getId());
            if(item.getIs_testable()=='Y') {
                items.add(itemService.findOne(c.getItem().getId()));
                item_imgs.add(itemService.itemImg(c.getItem().getId()));

                tmpItemOption = itemService.itemOptionList(c.getItem());
                for(Item_option item_option : tmpItemOption){
                    item_options.add(item_option);
                }

                tmpTestColor = testService.findByItem(c.getItem());
                for(TestColor testColor : tmpTestColor){
                    testColors.add(testColor);
                }
            }
        }
        model.addAttribute("items", items);
        model.addAttribute("item_img", item_imgs);
        model.addAttribute("item_options", item_options);
        model.addAttribute("testColors", testColors);

        return "test/test-cart";
    }
}