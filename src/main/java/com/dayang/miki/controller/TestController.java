package com.dayang.miki.controller;

import com.dayang.miki.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test/test-start";
    }

    @GetMapping("/test/{item_id}")
    public String testItems(@PathVariable("item_id") Long id, Model model){
        return "test/test-main";
    }
}