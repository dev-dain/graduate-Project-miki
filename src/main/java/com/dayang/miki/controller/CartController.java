package com.dayang.miki.controller;

import com.dayang.miki.domain.*;
import com.dayang.miki.service.CartService;
import com.dayang.miki.service.ItemService;
import com.dayang.miki.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;
    private final StoreService storeService;

    @GetMapping("/cartList")
    public String cartList(@RequestParam("store_id")Long store_id, Model model){
        List<Cart> carts= cartService.findAll();
        List<Item> items = cartService.getItem();
        List<Item_option> item_options = cartService.getItemOption();
        List<Item_img> imgs = itemService.getCartImg(items);
        Store store = storeService.findById(store_id);
        List<StoreQuantity> storeQuantities = new ArrayList<>();
        for (Item_option item_option : item_options){
             storeQuantities.add(itemService.storeQuantityList(item_option, store));
        }
        model.addAttribute("storeQuantity", storeQuantities);
        model.addAttribute("item_options", item_options);
        model.addAttribute("items", items);
        model.addAttribute("carts", carts);
        model.addAttribute("imgs", imgs);

        return "cart/cart";
    }
    @PostMapping("/cart/{cart_id}")
    public String updateCart(@PathVariable("cart_id") Long id, @RequestBody CartItem cartItem){

        Optional<Cart> cart = cartService.findOne(id);
        cartService.updateCartNum(cartItem.getCount(), cart.get().getItem_option());
        return "redirect:/cart/cart";
    }

    @PostMapping("/cart")
    public String insertCart(@RequestBody CartItem cartItem){

        Long item_id = Long.valueOf(cartItem.getItem_id());
        Long item_option_id = Long.valueOf(cartItem.getItem_option_id());
        int count = cartItem.getCount();

        Item item = itemService.findOne(item_id);
        Item_option item_option = itemService.findItemOptionById(item_option_id);
        Cart cart = new Cart();
        cart.setItem(item);
        cart.setItem_option(item_option);
        cart.setCount(count);
        cartService.validate(cart);

        return "redirect:/item/item_id/item_option";
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
