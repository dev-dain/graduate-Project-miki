package com.dayang.miki.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("dev/category")
@RequiredArgsConstructor
public class DevCartController {
    private final DevCategoryService devCategoryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<CategoryDTO> bigCategory(){
        List<CategoryDTO> category = devCategoryService.bigCategory();
        return category;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{categoryId}")
    public JSONObject categories(@PathVariable("categoryId")String id){
        JSONObject jsonObject = new JSONObject();
        Map<Long, List<CategoryDTO>> map = new HashMap<>();

        Long categoryId = Long.parseLong(id);
        List<CategoryDTO> firstCategoryList = devCategoryService.firstCategory(categoryId);


        map.put(categoryId, firstCategoryList);
        for(CategoryDTO c : firstCategoryList){
            map.put(c.getCategoryId(), devCategoryService.secondCategory(c.getCategoryId()));
        }
        jsonObject.putAll(map);
        return jsonObject;
    }
}
