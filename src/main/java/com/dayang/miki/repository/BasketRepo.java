package com.dayang.miki.repository;

import com.dayang.miki.domain.Basket;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;


public interface BasketRepo extends JpaRepository<Basket, Long> {
    @Modifying
    @Query(
            value = "truncate table basket",
            nativeQuery = true
    )
    void truncateBasket();

    @Modifying
    @Query("UPDATE Basket b SET b.count = b.count + :cnt WHERE b.id = :id")
    int updateCnt(@Param("cnt") int cnt, @Param("id")Long id);
}
