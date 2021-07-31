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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final StoreService storeService;

    @GetMapping("/") // home page
    // 로그인이 되어 있어도 무조건 여기로 이동?
    public String index(){ return "login/login";}

    @GetMapping("/login")
    public String login(@RequestParam("id")String id, @RequestParam("code")String code, Model model, RedirectAttributes rttr){
        Store store = storeService.findSingleStore(id);
        if(store==null) return "login/fail";
        if(store.getCode().equals(code)){
            rttr.addFlashAttribute("store", store);

            return "redirect:/login-main?";
        }
        return "login/fail";
    }
    @GetMapping("/admin-check")
    public String adminCheck(@RequestParam("store_id")Long id){

        return "login/admin-check";
    }



    @GetMapping("/login-main")
    public String loginMain() {
        return "login-index";
    }

    @GetMapping("/main") // home page
    public String main(){
        return "index";
    }
}