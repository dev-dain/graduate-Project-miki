package com.dayang.miki.repository;

import com.dayang.miki.domain.Item_img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemImgRepository extends JpaRepository<Item_img,Long> {

    @Query(nativeQuery =true,
            value = "select i.* from item_img i where item_id = (:id) Limit 1")
    Item_img itemImg(@Param("id")Long id);

}
