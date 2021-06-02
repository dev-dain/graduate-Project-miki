package com.dayang.miki.repository;

import com.dayang.miki.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final EntityManager em;

    public void save(OrderItem orderItem){
        em.persist(orderItem);
    }

    public Long findMaxId(){
        return (Long) em.createQuery("select max(oi.id) from OrderItem oi ")
                .getSingleResult();
    }
}
