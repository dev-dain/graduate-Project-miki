package com.dayang.miki.Item;

import com.dayang.miki.domain.Category;
import com.dayang.miki.domain.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DevItemRepository extends JpaRepository<Item, Long> {


    List<Item> findByCategoriesIn(List<Category> category, Pageable pageable);

    Optional<Item> findById(Long id);
}
