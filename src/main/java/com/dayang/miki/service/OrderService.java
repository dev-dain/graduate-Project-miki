package com.dayang.miki.service;


import com.dayang.miki.domain.OrderItem;
import com.dayang.miki.domain.OrderStatus;
import com.dayang.miki.domain.Orders;
import com.dayang.miki.domain.Store;
import com.dayang.miki.repository.OrderItemRepository;
import com.dayang.miki.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Long order(Orders orders){
        orderRepository.save(orders);
        return orders.getId();
    }
    @Transactional
    public Integer thisWeekSales(Store store){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Date date1 = new Date(System.currentTimeMillis());
        date1.setHours(11);
        date1.setMinutes(59);
        date1.setSeconds(59);

        List<Orders> orders = new ArrayList<>();

        if(day == 1) {
            return todaySales(store);
        }
        else if(day==2) {
            calendar.add(Calendar.DATE, -1);
            Date date2 = calendar.getTime();
            date2.setHours(0);
            date2.setMinutes(0);
            date2.setSeconds(0);
            orders = orderRepository.weekSales(date1,date2, OrderStatus.ORDER, store);
        }

        else if(day==3){
            calendar.add(Calendar.DATE, -2);
            Date date2 = calendar.getTime();
            date2.setHours(0);
            date2.setMinutes(0);
            date2.setSeconds(0);
            orders = orderRepository.weekSales(date1, date2, OrderStatus.ORDER, store);

        }
        else if(day==4){
            calendar.add(Calendar.DATE, -3);
            Date date2 = calendar.getTime();
            date2.setHours(0);
            date2.setMinutes(0);
            date2.setSeconds(0);
            orders = orderRepository.weekSales(date1, date2, OrderStatus.ORDER, store);

        }
        else if(day==5){
            calendar.add(Calendar.DATE, -4);
            Date date2 = calendar.getTime();
            date2.setHours(0);
            date2.setMinutes(0);
            date2.setSeconds(0);
            orders = orderRepository.weekSales(date1, date2, OrderStatus.ORDER, store);

        }
        else if(day==6){
            calendar.add(Calendar.DATE, -5);
            Date date2 = calendar.getTime();
            date2.setHours(0);
            date2.setMinutes(0);
            date2.setSeconds(0);
            orders = orderRepository.weekSales(date1, date2, OrderStatus.ORDER, store);

        }
        else if(day==7){
            calendar.add(Calendar.DATE, -6);
            Date date2 = calendar.getTime();
            date2.setHours(0);
            date2.setMinutes(0);
            date2.setSeconds(0);
            orders = orderRepository.weekSales(date1, date2, OrderStatus.ORDER, store);

        }
        int weekSales = 0;
        for(Orders orders1 : orders){
            weekSales +=orders1.getPay();
        }
        return weekSales;
    }

    @Transactional
    public Integer todaySales(Store store){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date date = calendar.getTime();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        List<Orders> orders = orderRepository.todaySales(date, OrderStatus.ORDER, store);
        int todaySales =0;
        for(Orders order : orders){
            todaySales += order.getPay();
        }
        return todaySales;
    }

    @Transactional
    public Orders findOne(Long id){
        return orderRepository.findOne(id);
    }
    @Transactional
    public void orderItem(OrderItem orderItem){
        orderItemRepository.save(orderItem);
    }
    @Transactional
    public Long getMaxOrderNum(){
        return orderItemRepository.findMaxId();
    }
}
