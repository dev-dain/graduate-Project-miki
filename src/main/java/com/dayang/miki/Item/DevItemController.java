package com.dayang.miki.Item;

import com.dayang.miki.Item.DTO.ImageDTO;
import com.dayang.miki.Item.DTO.ItemDTO;
import com.dayang.miki.Item.DTO.ItemPopularDTO;
import com.dayang.miki.Item.DTO.OptionDTO;
import com.dayang.miki.category.CategoryDTO;
import com.dayang.miki.category.DevCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    List<Long> randomNumber = DevItemService.rand();

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

        jsonObject.put("ItemList", itemDTOList);
        return jsonObject;
    }

    @GetMapping("/item/{itemId}")
    public JSONObject item(@PathVariable("itemId")Long id){
        JSONObject jsonObject = new JSONObject();
        ItemDTO itemDTO = devItemService.findOneItem(id);
        if(itemDTO==null){
            jsonObject.put("no such item", null);
        }

        else jsonObject.put("Item", itemDTO);

       return  jsonObject;
    }

    @GetMapping("/item/{itemId}/detail")
    public JSONObject productImage(@PathVariable("itemId")Long id){

        JSONObject jsonObject = new JSONObject();
        List<ImageDTO> imageDTOList = devItemService.productImage(id);
        jsonObject.put("ItemDetail", imageDTOList);

        return jsonObject;
    }
    @GetMapping("/item/{itemId}/option")
    public JSONObject itemOption(@PathVariable("itemId")String id, @RequestParam("storeId")Long storeId){
        JSONObject jsonObject = new JSONObject();
        Long itemId = Long.parseLong(id);
        List<OptionDTO> optionDTOList = devItemService.itemOption(itemId, storeId);
        jsonObject.put("ItemOption", optionDTOList);

        return jsonObject;
    }
    @GetMapping("/search")
    public JSONObject searchVoice(@RequestParam("keyword") String keyword,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "sort", defaultValue = "id") String sort){

        JSONObject jsonObject = new JSONObject();
        List<ItemDTO> itemDTOList = devItemService.searchItem(keyword, pageNum, sort);
        jsonObject.put("Item", itemDTOList);

        return jsonObject;
    }

    @GetMapping("/popularity")
    public JSONObject popularity(){
        JSONObject jsonObject = new JSONObject();
        List<ItemPopularDTO> itemPopularDTOList = devItemService.popularity();
        jsonObject.put("Item", itemPopularDTOList);
        return jsonObject;
    }

    @GetMapping("/mdItem")
    public JSONObject mdsItem(){
        JSONObject jsonObject = new JSONObject();
        List<ItemDTO> itemDTOList = devItemService.mdsItem(randomNumber);
        jsonObject.put("Item", itemDTOList);
        return jsonObject;
    }
    @GetMapping("/bestSeller")
    public JSONObject bestSeller(){
        JSONObject jsonObject = new JSONObject();
        List<ItemDTO> itemDTOList = devItemService.bestSeller();
        jsonObject.put("Item", itemDTOList);
        return jsonObject;
    }

}
