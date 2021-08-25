package com.dayang.miki.category;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("/dev")
@RequiredArgsConstructor
public class DevCartController {
    private final DevCategoryService devCategoryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/category")
    public List<CategoryDTO> bigCategory(){
        List<CategoryDTO> category = devCategoryService.bigCategory();
        return category;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/category/{categoryId}")
    public JSONObject categories(){
        JSONObject jsonObject = new JSONObject();
        return null;
    }
}
