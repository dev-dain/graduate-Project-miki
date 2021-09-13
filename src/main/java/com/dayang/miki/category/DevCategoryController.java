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
        Long id = Long.parseLong(categoryId);
        CategoryDTO categoryDTO = devCategoryService.categoryDTO(devCategoryService.findById(id));
        List<CategoryDTO> categoryDTOList = devCategoryService.categoryChild(id);
        jsonObject.put("category", categoryDTOList);
        jsonObject.put("size", categoryDTOList.size());
        jsonObject.put("name", categoryDTO.getCategoryName());

        return jsonObject;
    }
}
