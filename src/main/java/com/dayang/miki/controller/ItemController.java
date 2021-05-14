package com.dayang.miki.controller;

import com.dayang.miki.domain.*;
import com.dayang.miki.service.ItemService;
import com.dayang.miki.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final StoreService storeService;
    private final Long store_id = 1L;
    @GetMapping("/categoryList")
    public String searchItem(){ return "searchItem/categoryList"; }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, value ="/category/{list}")
    public String categoryItem(@PathVariable("list") Long category_id, Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum){
        Category category = itemService.findOneCategory(category_id);
        List<Category> categories = itemService.getFriendCategory(category);

        List<Category> showCategory = new ArrayList<>();
        List<Category> tmp;
        for(Category c : categories){
            tmp = itemService.getFriendCategory(c);
            showCategory.add(c);
            if(tmp.size()!=0){
                for(Category cc : tmp){
                    showCategory.add(cc);
                }
            }
        }
        if(itemService.getByCategory(category_id).size()!=0){
            showCategory.add(category);
        }
        List<Item> items;
        items = itemService.findItemByCategory(showCategory, pageNum);
        long maxNum = itemService.getItemNum(showCategory);
        model.addAttribute("maxNum", maxNum);
        model.addAttribute("category", category_id);
        model.addAttribute("item", items);


        return "searchItem/category";
    }

    @GetMapping("/item/{item_id}")
    public String Item(@PathVariable("item_id")Long id, Model model){
        Item item = itemService.findOne(id);
        itemService.save(item);

        Item_img item_img = itemService.itemImg(item);
        int cnt = itemService.reviewCnt(item);
        double rate = itemService.reviewRate(item);
        model.addAttribute("item_id", item.getId());
        model.addAttribute("item_name",item.getName());
        model.addAttribute("item_img", item_img);
        model.addAttribute("item_price", item.getItem_price());
        model.addAttribute("item_discountPrice", item.getDiscount_price());
        model.addAttribute("item_isTestable", item.getIs_testable());
        model.addAttribute("review_rate", rate);
        model.addAttribute("review_count", cnt);

        return "searchItem/item";
    }
    @GetMapping("/item/{item_id}/detail")
    public String ItemDetail(@PathVariable("item_id") Long id, Model model){
        Item item = itemService.findOne(id);
        List<Product_img> product_img = itemService.productImgs(item);
        model.addAttribute("item_id", item.getId());
        model.addAttribute("item_name", item.getName());
        model.addAttribute("item_isTestable", item.getIs_testable());
        model.addAttribute("product_img",product_img);
        return "searchItem/item-detail";
    }

    @GetMapping("/item/{item_id}/item_option")
    public String ItemOption(@PathVariable("item_id") Long id, Model model){
        Item item = itemService.findOne(id);
        Store store = storeService.findById(store_id);
        List<StoreQuantity> storeQuantities = itemService.storeQuantityList(item, store);
        List<Item_option> item_options = itemService.itemOptionList(item);
        model.addAttribute("item_option", item_options);
        model.addAttribute("store_Quantity", storeQuantities);
        return "searchItem/item-detail";
    }

    @GetMapping("/searchVoice")
    public String searchVoice(){
        return "searchItem/voice-search";
    }

    @GetMapping("/searchItem/{keyword}")
    public String search(@PathVariable("keyword")String keyword, Model model){
        if(itemService.findByItemName(keyword).size()>0) {
            List<Item> items = itemService.findByItemName(keyword);
            List<Item_img> item_imgs = new ArrayList<>();
            for(Item item : items){
                item_imgs.add(itemService.itemImg(item));
            }
            model.addAttribute("item",itemService.findByBrandName(keyword));
            model.addAttribute("item_img", item_imgs);
            model.addAttribute("item_count", items.size());
        }
        if(itemService.findByBrandName(keyword)!=null)model.addAttribute("brand", itemService.findByBrandName(keyword));
        if(itemService.getCategoryByName(keyword)!=null)model.addAttribute("category", itemService.getCategoryByName(keyword));

        return "searchItem/search-result";
    }
}