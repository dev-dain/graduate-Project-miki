package com.dayang.miki.controller;

import com.dayang.miki.domain.Item;
import com.dayang.miki.repository.ItemSearch;
import com.dayang.miki.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

   /* @GetMapping
    public String searchItem(@ModelAttribute("itemSearch")ItemSearch itemSearch, Model model){
        List<Item> items = ItemService.findByKeyword(itemSearch);
        model.addAllAttributes("item", items);
        return "/search";
    }*/
}
