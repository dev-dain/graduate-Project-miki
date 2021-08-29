package com.dayang.miki.Item;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_img;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevItemImageRepository extends JpaRepository<Item_img, Long> {

    Item_img findTop1ByItem (Item item);
}
