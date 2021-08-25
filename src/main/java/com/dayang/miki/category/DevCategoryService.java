package com.dayang.miki.category;

import com.dayang.miki.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DevCategoryService {

    private final DevCategoryRepository repository;

    public List<CategoryDTO> bigCategory(){
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        List<Category> category = repository.findByParentIsNull();

        for(Category c : category){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryId(c.getId());
            categoryDTO.setCategoryName(c.getName());
            categoryDTOList.add(categoryDTO);
        }

        return categoryDTOList;
    }

    public Category findById(Long id){
        Optional<Category> category = repository.findById(id);
        return category.get();
    }

    public List<Category> findByParent(Category category){
        List<Category> categories = new ArrayList<>();
        try{
            categories = repository.findByParent(category);
        }catch (NoResultException e){
            categories =null;
        }
        return categories;
    }

    public List<CategoryDTO> firstCategory(Long id){
        Category category = findById(id);

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        List<Category> categories = findByParent(category);

        for(Category c : categories){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryId(c.getId());
            categoryDTO.setCategoryName(c.getName());
            categoryDTOList.add(categoryDTO);
        }

        return categoryDTOList;
    }

    public List<CategoryDTO.CategoryParent> secondCategory(Long id){

        Category category = findById(id);

        List<CategoryDTO.CategoryParent> categoryDTOList = new ArrayList<>();
        List<Category> categories = findByParent(category);
        CategoryDTO categoryDTO = new CategoryDTO();

/*        for(Category c : categories){
            CategoryDTO.CategoryParent categoryDTO = categoryDTO.cate;
            categoryDTO.setCategoryId(c.getId());
            categoryDTO.setCategoryName(c.getName());
            categoryDTOList.add(categoryDTO);
        }*/

        return categoryDTOList;
    }

}
