package com.dayang.miki.Item.reository;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevItemOptionRepository extends JpaRepository<Item_option, Long> {
    List<Item_option> findByItem(Item item);
}
