package com.dayang.miki.repository;

import com.dayang.miki.domain.Category;
import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_img;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findByNameContaining(String name, Pageable pageable);

    @Query(nativeQuery =true,
            value = "select count(i.item_id) from item i inner join category_item c \n" +
            "on i.item_id = c.item_id\n" +
            "where category_id in (:ids);")
    long countItemNum(@Param("ids")List<Category> ids);

    @Query(nativeQuery =true,
            value = "select i.* from item i inner join category_item c \n" +
            "on i.item_id = c.item_id\n" +
            "where category_id in (:ids) ")
    List<Item> findItemByIn(@Param("ids") List<Category> ids, Pageable pageable);


   /* @Modifying
    @Query("UPDATE Item i set i.is_testable ='Y' where i.id =:id")
    void updateShowCount(@Param("id") Long id);*/
}
