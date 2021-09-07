package com.dayang.miki.test;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_option;
import com.dayang.miki.domain.TestColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevTestRepository extends JpaRepository<TestColor, Long> {

    TestColor findByItemOption(Item_option itemOption);
}
