package com.dayang.miki.controller;

import com.dayang.miki.domain.Position;
import com.dayang.miki.domain.Store;
import com.dayang.miki.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/stores")
    public String stores(Model model){
        List<Store> stores = storeService.findAllStore();
        List<Position> positions = storeService.findAllPosition();
        model.addAttribute("stores", stores);
        model.addAttribute("positions", positions);
        return "store/stores";
    }

    @GetMapping("/admin/login")
    public String login(@PathVariable("password") String passwrd, @PathVariable("store_name") String name){

        return "redirect:/";
    }
}