package com.dayang.miki.controller;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Store;
import com.dayang.miki.service.ItemService;
import com.dayang.miki.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final StoreService storeService;

    @GetMapping("/") // home page
    public String index(){ return "login/login";}

    @GetMapping("/login")
    public String login(@RequestParam("id")String id, @RequestParam("code")String code, Model model){
        Store store = storeService.findSingleStore(id);
        if(store.getCode().equals(code)){
            model.addAttribute("store", store);
            return "redirect:/main";
        }
        return "login/fail";
    }

    @GetMapping("/main") // home page
    public String main(){ return "index";}

}