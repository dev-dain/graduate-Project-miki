package com.dayang.miki.controller;

import com.dayang.miki.domain.Basket;
import com.dayang.miki.domain.Item_img;
import com.dayang.miki.service.BasketService;
import com.dayang.miki.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final ItemService itemService;

    @GetMapping("/basket")
    public String basket(Model model){
        List<Basket> baskets = basketService.findAll();
        List<Item_img> imgs = new ArrayList<>();
        for(Basket basket : baskets){
            imgs.add(itemService.itemImg(basket.getItem()));
        }
        model.addAttribute(baskets);
        model.addAttribute(imgs);
        return "/basket/basket";
    }

    @DeleteMapping("/basket/{basket_id}")
    public String deleteOne(@PathVariable("basket_id") Long id){
        basketService.deleteOne(id);
        return "redirect:/basket/basket";
    }
    @DeleteMapping("/basket")
    public String deleteAll(){
        basketService.truncateBasket();
        return "redirect:/basket/basket";
    }

}
