package com.dayang.miki.repository;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_img;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface ItemImgRepository extends JpaRepository<Item_img,Long> {

    @Query(nativeQuery =true,
            value = "select i.* from item_img i where item_id = (:id) Limit 1")
    Item_img itemImg(@Param("id")Long id);

    @Query("select distinct i from Item_img i where i.item in (:items)")
    List<Item_img> itemImgcart(@Param("items") List<Item> items);
}
