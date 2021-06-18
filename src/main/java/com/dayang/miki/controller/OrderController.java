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
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final CartService cartService;

    @PostMapping("/order")
    public String order(@RequestBody List<Cart> cart, Model model){

        int price =0;
        for(Cart cart1 : cart){
            price += (cart1.getItem().getItem_price() - cart1.getItem().getDiscount_price()) * cart1.getCount();
            itemService.updateStockQuantity(cart1.getItem_option().getStockQuantity() - cart1.getCount(), cart1.getItem_option().getId());

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
        }

        model.addAttribute("order_code", "D97AY998AN9G" + order_id );
        return "order/order_success";
    }
    @GetMapping("/orderList")
    public String cartList(Model model){
        List<Cart> carts= cartService.findAll();
        List<Item> items = cartService.getItem();
        List<Item_option> item_options = cartService.getItemOption();
        List<Item_img> imgs = itemService.getCartImg(items);

        model.addAttribute("item_options", item_options);
        model.addAttribute("items", items);
        model.addAttribute("carts", carts);
        model.addAttribute("imgs", imgs);

        return "order/orderList";
    }
}
