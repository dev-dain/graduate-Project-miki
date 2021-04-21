package com.dayang.miki.repository;

import com.dayang.miki.domain.Cart;
import com.dayang.miki.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepository {
    private final EntityManager em;

    public void save(Cart cart){ em.persist(cart); }

    public List<Cart> findAll(){
        return em.createQuery("select c from Cart c", Cart.class)
                .getResultList();
    }
    public Cart findOne(Item item){
        return em.createQuery("select c from Cart c where c.item =:item", Cart.class)
                .setParameter("item", item)
                .getSingleResult();
    }

}
