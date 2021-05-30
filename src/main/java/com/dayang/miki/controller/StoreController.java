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

    @GetMapping("/storeDistance/{store_id}")
    public String distance(@PathVariable("store_id")Long id, Model model){
        Store store = storeService.findById(id);
        Position position = storeService.findSinglePosition(store);
        List<Store> stores = storeService.findAllStore();
        List<Position> positions = storeService.findAllPosition();
        List<Double> dist = new ArrayList<>();
        for(Position position1 : positions){
            dist.add(storeService.positionDist(position.getLatitude(), position1.getLatitude(),
                    position.getLongitude(), position1.getLongitude()));
        }
        model.addAttribute("store", stores);
        model.addAttribute("distance",dist); //같은 인덱스 순서대로 들어있어용
        model.addAttribute("positions", positions);

        return "store/store-content";
    }

    // 임시로 만든 login 
    @GetMapping("/login")
    public String store_login(){
        return "login/login";
    }

    @GetMapping("/admin/login")
    public String login(@PathVariable("password") String passwrd, @PathVariable("store_name") String name){

        return "redirect:/";
    }
}