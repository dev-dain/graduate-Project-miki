package com.dayang.miki.service;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.OrderItem;
import com.dayang.miki.domain.Orders;
import com.dayang.miki.domain.PayMethod;
import com.dayang.miki.repository.ItemRepository;
import com.dayang.miki.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long itemId, int count, PayMethod pay){

        //엔티티 조회
        Item item = itemRepository.findOne(itemId);

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, (item.getItem_price() - item.getDiscount_price()), count);

        //주문 생성
        Orders orders = Orders.createOrder(pay, orderItem);

        orderRepository.save(orders);

        return orders.getId();
    }

    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId){
        Orders orders = orderRepository.findOne(orderId);
        orders.cancel();
    }
    //검색
}
