package com.dayang.miki.store;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dev")
@RequiredArgsConstructor
public class DevStoreController {

    private final DevStoreService devStoreService;

    @GetMapping("/stores")
    public JSONObject stores(@RequestParam("latitude")String latitude, @RequestParam("longitude")String longitude){
        JSONObject jsonObject = new JSONObject();
        List<StoreDTO> storeDTOList = devStoreService.nearStores(Double.parseDouble(latitude),Double.parseDouble(longitude) );
        jsonObject.put("Store", storeDTOList);
        return jsonObject;
    }
}
