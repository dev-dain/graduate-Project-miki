package com.dayang.miki.controller;

import com.dayang.miki.domain.Item;
import com.dayang.miki.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/") // home page
    public String index(){ return "index";}

}