package com.dayang.miki.controller;

import com.dayang.miki.domain.*;
import com.dayang.miki.service.ItemService;
import com.dayang.miki.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final ItemService itemService;

    @GetMapping("/test")
    public String test(){
        return "test/test-start";
    }

//    @GetMapping("/test/{item_id}")
//    public String singleTestItem(@PathVariable("item_id") Long id, Model model){
//        return "test/test-main";
//    }
//

    @GetMapping("/test/{item_id}")
    public String ItemOption(@PathVariable("item_id") Long id, Model model){
        Item item = itemService.findOne(id);
        itemService.save(item);

        Item_img item_img = itemService.itemImg(item.getId());

        model.addAttribute("item_id", item.getId());
        model.addAttribute("item_name",item.getName());
        model.addAttribute("item_img", item_img);

        List<Item_option> item_options = itemService.itemOptionList(item);
        model.addAttribute("item_option", item_options);

        return "test/test-main";
    }



    @GetMapping("/testAll/{cart_id}")
    public String testItems(@PathVariable("item_id") Long id, Model model){
        return "test/test-main";
    }
}