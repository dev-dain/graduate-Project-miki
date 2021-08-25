package com.dayang.miki.category;

import com.dayang.miki.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DevCategoryServiceTest {

    @Autowired
    DevCategoryService service;

    @Test
    public void BigCategoryTest(){
        //given
        //when
        List<CategoryDTO> categoryDTOList = service.bigCategory();
        //then
        assertThat(categoryDTOList.size()).isEqualTo(11);
        assertThat(categoryDTOList.get(0).getCategoryId()).isEqualTo(1L);
    }
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
    public void firstCategoryTest(){
        //given
        Long id = 1L;
        //when
        List<CategoryDTO> firstCategoryList = service.firstCategory(id);
        //then
        assertThat(firstCategoryList.size()).isEqualTo(4);

    }
}