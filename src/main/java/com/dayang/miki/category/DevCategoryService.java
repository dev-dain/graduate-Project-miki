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

    public List<Category> findByParent(Category category){
        return repository.findByParent(category);
    }

    public List<CategoryDTO> categoryChild(Long categoryId){
        Category parent = findById(categoryId);
        List<Category> categoryList = findByParent(parent);

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for(Category category : categoryList){
            categoryDTOList.add(categoryDTO(category));
        }
        return categoryDTOList;
    }

}
