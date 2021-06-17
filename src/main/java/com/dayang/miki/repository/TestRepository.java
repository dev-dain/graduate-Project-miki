package com.dayang.miki.repository;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.TestColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<TestColor, Long> {
    List<TestColor> findByItem(Item item);
}
