package com.dayang.miki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/") // home page
    public String index(){
        return "index";
    }
    @GetMapping("order") // 바로 결제
    public String order(){
        return "categoryList";
    }
    @GetMapping("test") //테스트
    public String test(){ return "categoryList"; }
    @GetMapping("categoryList") // 상품 검색
    public String categoryList(){
        return "categoryList";
    }
}
