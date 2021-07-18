package com.dayang.miki.repository;

import com.dayang.miki.domain.Item_option;
import com.dayang.miki.domain.StoreQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface StoreQuantityRepository extends JpaRepository<StoreQuantity, Long> {
    @Query("select sq from StoreQuantity sq where sq.item_option =:item_oprion")
    List<StoreQuantity> findByItemOptions(@RequestParam("item_option") Item_option item_option);
}
