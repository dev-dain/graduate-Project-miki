package com.dayang.miki.item;

import com.dayang.miki.Item.DevItemService;
import com.dayang.miki.Item.ItemDTO;
import com.dayang.miki.category.DevCategoryService;
import com.dayang.miki.domain.Category;
import com.dayang.miki.domain.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DevItemServiceTest {

    @Autowired
    DevItemService devItemService;
    @Autowired
    DevCategoryService devCategoryService;

    @Test
    void findByCategory(){
        //given
        Category category = devCategoryService.findById(6L);

        //when
        List<ItemDTO> item = devItemService.findByCategory(category);
        //then
        assertThat(item.size()).isEqualTo(19);
    }

}