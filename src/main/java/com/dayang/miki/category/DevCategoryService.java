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


    public Category findById(Long id){
        Optional<Category> category = repository.findById(id);
        return category.get();
    }

    public CategoryDTO categoryDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setCategoryName(category.getName());
        categoryDTO.setCategoryId(category.getId());
        return categoryDTO;
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
        if(categories!=null) {
            for (Category c : categories) {
                categoryDTOList.add(categoryDTO(c));
            }
        }
        return categoryDTOList;
    }

    public List<CategoryDTO> secondCategory(Long id){

        Category category = findById(id);

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        List<Category> categories = findByParent(category);
        if(categories!=null){
            for(Category c : categories){
                categoryDTOList.add(categoryDTO(c));
            }
        }
        else return null;
        return categoryDTOList;
    }

}
