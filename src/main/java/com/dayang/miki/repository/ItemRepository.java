package com.dayang.miki.repository;

import com.dayang.miki.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //상품 검색
    public Item findOne(Long id){return em.find(Item.class, id); }
/*
    public Item findByKeyword(ItemSearch itemSearch ){
        Item item;

        return query
    }*/

    //카테고리별 검색
    public List<Item> findCategories(String category){
        return em.createQuery("select i from Item i where i.categories =:category", Item.class)
                .setParameter("category", category)
                .getResultList();
    }

}
