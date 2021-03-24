package com.dayang.miki.repository;

import com.dayang.miki.domain.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Orders orders){
        em.persist(orders);
    }
    public Orders findOne(Long id){
        return em.find(Orders.class, id);
    }

}
