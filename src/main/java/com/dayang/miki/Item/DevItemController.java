package com.dayang.miki.Item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("dev")
@RequiredArgsConstructor
public class DevItemController {
    private final DevItemService devItemService;

    @GetMapping("/category/{categoryId}/itemList")
    public List<ItemDTO> itemList(){

        return null;
    }
}
