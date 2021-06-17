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
        List<TestColor> testColors = testService.findByItem(item);
        model.addAttribute("testColor", testColors);
        return "test/test-main";
    }



/*    @GetMapping("/testAll")
    public String testItems( Model model){
        List<Cart> carts = cartService.findAll();
        List<TestColor> testColors = new ArrayList<>();
        for(Cart c : carts){
            if(c.getItem().getIs_testable()=='Y') testColors.add(testService.findByItem(c.getItem()));
        }
        return "test/test-main";
    }*/
}