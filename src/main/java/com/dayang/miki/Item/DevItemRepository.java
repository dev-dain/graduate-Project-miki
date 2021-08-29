package com.dayang.miki.Item;

import com.dayang.miki.category.CategoryDTO;
import com.dayang.miki.domain.Category;
import com.dayang.miki.domain.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevItemRepository extends JpaRepository<Item, Long> {


    List<Item> findByCategoriesIn(List<Category> category, Pageable pageable);

}
