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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String testItems(@RequestParam(value = "pageNum", defaultValue = "1")int pageNum, Model model){
        List<Cart> carts = cartService.findTestableCart(pageNum);

        List<TestItem> testItems = new ArrayList<>();

        for(Cart c : carts){
            Item item = itemService.findOne(c.getItem().getId());
            Item_img item_img = itemService.itemImg(item.getId());
            List<Item_option> item_options = itemService.itemOptionList(item);
            List<TestColor> testColors = testService.findByItem(item);
            for(int i=0; i<item_options.size(); i++){
                TestItem testItem = new TestItem(item.getId(), item.getName(), item_img.getItem_img(), item_options.get(i).getId(), item_options.get(i).getItem_option_name(),
                        testColors.get(i).getColorCode(), testColors.get(i).getAlpha(), testColors.get(i).getFace_location());
                testItems.add(testItem);
            }
        }

        model.addAttribute(testItems);
        return "test/test-cart";
    }
}