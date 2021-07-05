package com.dayang.miki.controller;

import com.dayang.miki.domain.*;
import com.dayang.miki.service.CartService;
import com.dayang.miki.service.ItemService;
import com.dayang.miki.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final CartService cartService;

    @PostMapping("/order")
    public String order(@RequestBody List<Cart> cart){

        int price =0;
        for(Cart cart1 : cart){
            price += (cart1.getItem().getItem_price() - cart1.getItem().getDiscount_price()) * cart1.getCount();
            itemService.updateStockQuantity(cart1.getItem_option().getStockQuantity() - cart1.getCount(), cart1.getItem_option().getId());
            itemService.updateOrderCnt(cart1.getItem().getId(), cart1.getCount() + cart1.getItem().getOrderCnt());

        }
        java.util.Date time = new java.util.Date(System.currentTimeMillis());

        Orders orders = new Orders();
        orders.setPay(price);
        orders.setOrderDate(time);
        orders.setStatus(OrderStatus.ORDER);
        Long order_id = orderService.order(orders);


        Orders order = orderService.findOne(order_id);


        for(Cart cart1 : cart){
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(cart1.getItem());
            orderItem.setItem_option(cart1.getItem_option());
            orderItem.setOrder(order);
            orderItem.setCount(cart1.getCount());
            price += (cart1.getItem().getItem_price() - cart1.getItem().getDiscount_price()) * cart1.getCount();
            orderItem.setOrderPrice(price);
            orderService.orderItem(orderItem);
        }

        return "order/order_success";
    }



    @GetMapping("/orderFail")
    public String orderFail(){
        return "orer/orderFail";
    }

    @GetMapping("/orderAllList")
    public String orderAllList(Model model){
        List<Cart> carts = cartService.findAll();
        List<Item> items = cartService.getItem();
        List<Item_option> item_options = cartService.getItemOption();
        List<Item_img> imgs = itemService.getCartImg(items);

        model.addAttribute("item_options", item_options);
        model.addAttribute("items", items);
        model.addAttribute("carts", carts);
        model.addAttribute("imgs", imgs);

        return "order/orderList";
    }

    @PostMapping("/orderSelectList")
    public String orderSelectList(@RequestBody List<Cart> cartList, Model model){

        List<Item> items =  new ArrayList<>();
        List<Item_option> item_options = new ArrayList<>();

        for(Cart cart : cartList){
            items.add(cartService.getSelectItem(cart.getId()));
            item_options.add(cartService.getSelectItemOption(cart.getId()));
        }
        List<Item_img> item_imgs = itemService.getCartImg(items);


        model.addAttribute("item_options", item_options);
        model.addAttribute("items", items);
        model.addAttribute("carts", cartList);
        model.addAttribute("imgs", item_imgs);

        return "order/orderList";
    }
}
