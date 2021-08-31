package com.dayang.miki.item;

import com.dayang.miki.Item.DevItemService;
import com.dayang.miki.Item.ItemDTO;
import com.dayang.miki.category.CategoryDTO;
import com.dayang.miki.category.DevCategoryService;

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
class DevItemServiceTest {

    @Autowired
    DevItemService devItemService;
    @Autowired
    DevCategoryService devCategoryService;

    @Test
    void findByCategory(){
        //given
        Long categoryId = Long.parseLong("1");
        List<CategoryDTO> categories = new ArrayList<>(); //카테고리 리스트
        List<ItemDTO> itemDTOList = new ArrayList<>(); //안에 들어갈 아이템 리스트

        //when
        ////////////////카테고리/////////////////////////
        CategoryDTO categoryDTO = devCategoryService.categoryDTO(devCategoryService.findById(categoryId));
        categories.add(categoryDTO);

        List<CategoryDTO> firstCategoryList = devCategoryService.firstCategory(categoryId);
        categories.addAll(firstCategoryList);

        for(CategoryDTO c : firstCategoryList){
            List<CategoryDTO> secondCategoryList = devCategoryService.secondCategory(c.getCategoryId());
            if(secondCategoryList!=null) categories.addAll(secondCategoryList);
        }

        ////////////////아이템//////////////////////////

        itemDTOList = devItemService.findByCategory(categories, 1, "id");


        //then
        assertThat(itemDTOList.size()).isEqualTo(9);
        assertThat(itemDTOList.get(0).getItemId()).isEqualTo(1);
    }

}