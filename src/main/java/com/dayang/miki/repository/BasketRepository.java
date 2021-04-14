package com.dayang.miki.repository;

import com.dayang.miki.domain.Basket;
import com.dayang.miki.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasketRepository {
    private final EntityManager em;

    public void save(Basket basket){ em.persist(basket); }

    public List<Basket> findAll(){
        return em.createQuery("select b from Basket b", Basket.class)
                .getResultList();
    }
    public Basket findOne(Item item){
        return em.createQuery("select b from Basket b where b.item =:item", Basket.class)
                .setParameter("item", item)
                .getSingleResult();
    }

}
