package com.dayang.miki.controller;

import com.dayang.miki.domain.Cart;
import com.dayang.miki.domain.Item_img;
import com.dayang.miki.service.CartService;
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
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;

    @GetMapping("/cart")
    public String cart(Model model){
        List<Cart> baskets = cartService.findAll();
        List<Item_img> imgs = new ArrayList<>();
        for(Cart basket : baskets){
            imgs.add(itemService.itemImg(basket.getItem()));
        }
        model.addAttribute(baskets);
        model.addAttribute(imgs);
        return "/cart/cart";
    }

    @DeleteMapping("/cart/{cart_id}")
    public String deleteOne(@PathVariable("cart_id") Long id){
        cartService.deleteOne(id);
        return "redirect:/cart/cart";
    }
    @DeleteMapping("/cart")
    public String deleteAll(){
        cartService.truncateCart();
        return "redirect:/cart/cart";
    }

}
