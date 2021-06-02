package com.dayang.miki.repository;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemOptionRepository extends JpaRepository<Item_option, Long> {

    @Modifying
    @Query("UPDATE Item_option SET stockQuantity =:stockQuantity WHERE id =:item_option_id")
    void updateStockQuantity(@Param("stockQuantity") int stockQuantity, @Param("item_option_id") Long item_option_id);
}
