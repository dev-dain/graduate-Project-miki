package com.dayang.miki.repository;

import com.dayang.miki.domain.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;


public interface CartRepo extends JpaRepository<Cart, Long> {
    @Modifying
    @Query(
            value = "truncate table cart",
            nativeQuery = true
    )
    void truncateCart();

    @Modifying
    @Query("UPDATE Cart c SET c.count = c.count + :cnt WHERE c.id = :id")
    int updateCnt(@Param("cnt") int cnt, @Param("id")Long id);


}
