package com.dayang.miki.repository;

import com.dayang.miki.domain.Category;
import com.dayang.miki.domain.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameContaining(String name);
    @Query(nativeQuery =true,value = "select i.* from item i inner join category_item c \n" +
            "on i.item_id = c.item_id\n" +
            "where category_id in (:ids) ")
    List<Item> findItemByIn(@Param("ids") List<Category> ids, Pageable pageable);
}
