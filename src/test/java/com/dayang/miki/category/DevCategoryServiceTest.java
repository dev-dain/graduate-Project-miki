package com.dayang.miki.category;

import com.dayang.miki.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DevCategoryServiceTest {

    @Autowired
    DevCategoryService service;


    @Test
    public void findByIdTest(){
        //given
        Long id = 1L;
        //when
        Category category = service.findById(id);
        //then
        assertThat(category.getParent()).isNull();

    }

    @Test
    public void findChildCategory(){
        //given
        Long categoryId1 = 1L;
        Long categoryId2 = 2L;
        Long categoryId3 = 6L;

        //when
        List<CategoryDTO> categoryDTOList1 = service.categoryChild(categoryId1);
        List<CategoryDTO> categoryDTOList2 = service.categoryChild(categoryId2);
        List<CategoryDTO> categoryDTOList3 = service.categoryChild(categoryId3);
        //then
        assertThat(categoryDTOList1.size()).isEqualTo(4);
        assertThat(categoryDTOList2.size()).isEqualTo(8);
        assertThat(categoryDTOList3.size()).isEqualTo(0);
    }
}