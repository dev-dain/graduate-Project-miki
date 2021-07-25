package com.dayang.miki.repository;

import com.dayang.miki.domain.OrderStatus;
import com.dayang.miki.domain.Orders;
import com.dayang.miki.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public List<Orders> todaySales(Date date, OrderStatus orderStatus, Store store){
        return em.createQuery("select o from Orders o where o.orderDate >=:date and o.status =:orderStatus and o.store=:store ", Orders.class)
                .setParameter("date", date)
                .setParameter("orderStatus", orderStatus)
                .setParameter("store", store)
                .getResultList();
    }

    public List<Orders> weekSales(Date date1, Date date2, OrderStatus orderStatus, Store store){
        return em.createQuery("select o from Orders  o where o.orderDate <=:date1 and " +
                "o.orderDate >=:date2 and o.store=:store and o.status =:orderStatus", Orders.class)
                .setParameter("date1", date1)
                .setParameter("date2", date2)
                .setParameter("store", store)
                .setParameter("orderStatus", orderStatus)
                .getResultList();
    }
    public void save(Orders order){
        em.persist(order);
    }
    public Orders findOne(Long id){
       return em.find(Orders.class , id);
    }
}
