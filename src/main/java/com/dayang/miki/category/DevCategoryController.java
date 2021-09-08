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
public class DevCategoryController {
    private final DevCategoryService devCategoryService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{categoryId}")
    public JSONObject categories(@PathVariable("categoryId")String categoryId){
        JSONObject jsonObject = new JSONObject();
        List<CategoryDTO> categoryDTOList = devCategoryService.categoryChild(Long.parseLong(categoryId));
        jsonObject.put("category", categoryDTOList);
        return jsonObject;
    }
}
