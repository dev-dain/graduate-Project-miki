package com.dayang.miki.Item;

import com.dayang.miki.category.CategoryDTO;
import com.dayang.miki.category.DevCategoryService;
import com.dayang.miki.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("dev")
@RequiredArgsConstructor
public class DevItemController {
    private final DevItemService devItemService;
    private final DevCategoryService devCategoryService;

    @GetMapping("/category/{categoryId}/itemList")
    public JSONObject itemList(@PathVariable("categoryId") String id,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "sort", defaultValue = "id") String sort){
        JSONObject jsonObject = new JSONObject();
        Long categoryId = Long.parseLong(id);
        List<CategoryDTO> categories = new ArrayList<>(); //카테고리 리스트
        List<ItemDTO> itemDTOList = new ArrayList<>(); //아이템 리스트

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
        itemDTOList = devItemService.findByCategory(categories, pageNum, sort);

        jsonObject.put("itemList", itemDTOList);
        return jsonObject;
    }

    @GetMapping("/item/{itemId}")
    public JSONObject item(@PathVariable("itemId")Long id){
        JSONObject jsonObject = new JSONObject();
        ItemDTO itemDTO = devItemService.findById(id);
        if(itemDTO==null){
            jsonObject.put("no such item", null);
        }
        else jsonObject.put("item", itemDTO);

       return  jsonObject;
    }
}
