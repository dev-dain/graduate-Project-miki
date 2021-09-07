package com.dayang.miki.test;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dev")
@RequiredArgsConstructor
public class DevTestController {

    private final DevTestService devTestService;

    @GetMapping("/testSingleColor")
    public JSONObject testSingleColor(@RequestParam("itemId")String itemId){
        JSONObject jsonObject = new JSONObject();
        TestItemDTO testItemDTO = devTestService.testItem(Long.parseLong(itemId));
        jsonObject.put("Item", testItemDTO);

        return jsonObject;
    }
}
