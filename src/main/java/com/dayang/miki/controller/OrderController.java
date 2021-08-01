package com.dayang.miki.controller;

import com.dayang.miki.domain.*;
import com.dayang.miki.service.CartService;
import com.dayang.miki.service.ItemService;
import com.dayang.miki.service.OrderService;
import com.dayang.miki.service.StoreService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final CartService cartService;
    private final StoreService storeService;

    @Transactional
    @PostMapping("/order")
    public String order(@RequestParam("store_id")Long id,  @RequestBody String cartList, Model model) throws UnsupportedEncodingException {
        String b = URLDecoder.decode(cartList, String.valueOf(StandardCharsets.UTF_8));
        b = b.replaceAll("%2C", "");
        b = b.replaceAll("%5B", "");
        b = b.replaceAll("%5D", "");
        b = b.substring(20);

        String[] ids = b.split(",");



        List<Long> longId = new ArrayList<>();
        for(String s : ids){
            longId.add(Long.parseLong(s));

        }
        List<Cart> cart  = new ArrayList<>();
        for(Long l :longId){
            cart.add(cartService.findOne(l));
        }
        Store store = storeService.findById(id);
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
        orders.setStore(store);
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

        cartService.truncateCart();
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

    @PostMapping(value = "/orderSelectList")
    public String orderSelectList(@RequestBody String cartList, Model model) throws UnsupportedEncodingException {
        String b = URLDecoder.decode(cartList, String.valueOf(StandardCharsets.UTF_8));
        b = b.replaceAll("%2C", "");
        b = b.replaceAll("%5B", "");
        b = b.replaceAll("%5D", "");
        b = b.substring(9);

        String[] ids = b.split(",");

        List<Long> longId = new ArrayList<>();
        for(String s : ids){
            longId.add(Long.parseLong(s));

        }
        List<Cart> carts = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Item_img>item_imgs = new ArrayList<>();
        List<Item_option>item_options = new ArrayList<>();

        for(Long l :longId){
            carts.add(cartService.findOne(l));
        }
        for(Cart cart : carts){
            items.add(itemService.findOne(cart.getItem().getId()));
            item_imgs.add(itemService.itemImg(cart.getItem().getId()));
            item_options.add(itemService.findItemOptionById(cart.getItem_option().getId()));
        }
        model.addAttribute("item_options", item_options);
        model.addAttribute("items", items);
        model.addAttribute("carts", carts);
        model.addAttribute("imgs", item_imgs);

        return "order/orderList";
    }
}
