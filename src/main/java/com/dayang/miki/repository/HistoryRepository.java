package com.dayang.miki.repository;

import com.dayang.miki.domain.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class HistoryRepository {

    private final EntityManager em;

    public void save(History history){em.persist(history);}
}
