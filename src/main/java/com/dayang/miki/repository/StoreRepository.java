package com.dayang.miki.repository;

import com.dayang.miki.domain.Position;
import com.dayang.miki.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class StoreRepository {

    private final EntityManager em;
    public Position getSinglePosition(Store store){
        return em.createQuery("select p from Position p where p.store =: store",Position.class)
                .setParameter("store", store)
                .getSingleResult();
    }

    public List<Position> getAllPosition(){
        return em.createQuery("select p from Position p", Position.class)
                .getResultList();
    }

    public List<Store> findAllStore(){
        return (List<Store>) em.createQuery("select s from Store s", Store.class)
                .getResultList();
    }
    public Store findSingleStore(String name){
        return em.createQuery("select s from Store s where s.store_name =: name", Store.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
