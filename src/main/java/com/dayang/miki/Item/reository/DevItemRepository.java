package com.dayang.miki.Item.reository;

import com.dayang.miki.domain.Category;
import com.dayang.miki.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DevItemRepository extends JpaRepository<Item, Long> {


    Page<Item> findByCategoriesIn(List<Category> category, Pageable pageable);


    Optional<Item> findById(Long id);

    List<Item> findByNameContaining(String keyword, Pageable pageable);

    List<Item> findByNameContaining(String keyword);

    List<Item> findTop10ByIdGreaterThanOrderByPopularityDesc(Long id);

    List<Item> findTop3ByIdGreaterThanOrderByOrderCnt(Long id);
}
