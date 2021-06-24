package com.dayang.miki.service;


import com.dayang.miki.domain.OrderItem;
import com.dayang.miki.domain.Orders;
import com.dayang.miki.repository.OrderItemRepository;
import com.dayang.miki.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
