package com.dayang.miki.controller;

import com.dayang.miki.domain.Item;
import com.dayang.miki.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/bigCategory/{category_id}")
    public String bigCategory(@PathVariable("category_id") Long category_id, Model model){
        List<Item> items = itemService.categoryItemList(category_id);
        model.addAttribute("items", items);
        return "test";
    }

}
