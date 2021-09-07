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
    @GetMapping("/{categoryId}")
    public JSONObject categories(@PathVariable("categoryId")String id){
        JSONObject jsonObject = new JSONObject();
        Map<String, List<CategoryDTO>> map = new HashMap<>();

        Long categoryId = Long.parseLong(id);
        List<CategoryDTO> firstCategoryList = devCategoryService.firstCategory(categoryId);


        map.put("parent " + Long.toString(categoryId), firstCategoryList);

        for(CategoryDTO c : firstCategoryList){
            List<CategoryDTO> list = devCategoryService.secondCategory(c.getCategoryId());
            if(list.size() != 0) map.put( "child " + Long.toString(c.getCategoryId()), list);
        }

        jsonObject.putAll(map);
        return jsonObject;
    }
}
