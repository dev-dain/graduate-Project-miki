package com.dayang.miki.service;

import com.dayang.miki.domain.Basket;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_option;
import com.dayang.miki.repository.BasketRepository;
import com.dayang.miki.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BasketRepository basketRepository;


}
