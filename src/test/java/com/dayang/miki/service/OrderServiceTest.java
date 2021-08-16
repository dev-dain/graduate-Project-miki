package com.dayang.miki.service;

import com.dayang.miki.controller.TestItem;
import com.dayang.miki.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    StoreService storeService;
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
    @Autowired
    ItemService itemService;
    @Autowired
    TestService testService;
    @Test
    void d(){
        int pageNum = 1;
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
        for(TestItem testItem : testItems){
            System.out.println("testItem = " + testItem);
        }
    }
    @Test
    void dd(){
        Store store = storeService.findById(1L);
        System.out.println(orderService.thisWeekSales(store));
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) ;
    }

    @Test
    public void te(){
        String id = "화곡점";
        String code = "AA65484ERG";
        Store store = storeService.findSingleStore(id);
        if(store.getCode().equals(code)) System.out.println("yes");
        else System.out.println(store.getCode());
        System.out.println(code);
    }

    @Test
    @Rollback(value = false)
    public void testOrder(){


        List<Cart> cart;
        cart = cartService.findAll();

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

        OrderItem orderItem = new OrderItem();

        for(Cart cart1 : cart){
            orderItem.setItem(cart1.getItem());
            orderItem.setItem_option(cart1.getItem_option());
            orderItem.setOrder(order);
            orderItem.setCount(cart1.getCount());
            price += (cart1.getItem().getItem_price() - cart1.getItem().getDiscount_price()) * cart1.getCount();
            orderItem.setOrderPrice(price);
            System.out.println(orderItem.getItem().getId());
        }
    }


    @Test
    public void idTest(){
        Long id = orderService.getMaxOrderNum();
        System.out.println(id);

    }
    @Test
    public void orderItem(){
        List<Cart> carts =cartService.findAll();
        int price = 0;
        Orders order = orderService.findOne(529L);
        for(Cart cart :carts){
            OrderItem orderItem = new OrderItem();
            price += (cart.getItem().getItem_price() - cart.getItem().getDiscount_price()) * cart.getCount();
            System.out.println(price);
            orderItem.setOrderPrice(price);
            orderItem.setCount(cart.getCount());
            orderItem.setItem(cart.getItem());
            orderItem.setItem_option(cart.getItem_option());
            orderItem.setOrder(order);
            orderService.orderItem(orderItem);
        }
    }
    @Test
    public void tessst(){
        List<Item> items = cartService.getItem();
    }
}
