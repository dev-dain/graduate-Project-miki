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
import org.springframework.transaction.annotation.Transactional;


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

    @Transactional
    @Modifying
    @Query("UPDATE Item SET is_testable = 'Y' where id =:id")
    void updateTestable(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Item SET reviewCnt =:review_cnt where id =:id")
    void updateReview(@Param("review_cnt")int review_cnt, @Param("id")Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Item SET popularity =:popularity where id =:id")
    void updatePopularity(@Param("popularity")int popularity, @Param("id")Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Item SET orderCnt =:order_cnt where id=:id")
    void updateOrderCnt(@Param("order_cnt")int order_cnt, @Param("id")Long id);

    @Transactional
    @Query(nativeQuery = true,
            value = "select * from item  order by popularity DESC Limit 10")
    List<Item> popularity();

    @Query(nativeQuery = true,
    value = "select * from item where item_date >=(:month) Limit 3")
    List<Item> newItem(@Param("month")String month);

}
