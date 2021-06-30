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

    @GetMapping("/categoryList")
    public String searchItem(){ return "searchItem/categoryList"; }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, value ="/category/{list}")
    public String categoryItem(@PathVariable("list") Long category_id, @RequestParam(value = "sort", defaultValue = "item_id") String sort, Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum){
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
        items = itemService.findItemByCategory(showCategory, pageNum, sort);
        long maxNum = itemService.getItemNum(showCategory);
        model.addAttribute("maxNum", maxNum);
        model.addAttribute("category", category_id);
        model.addAttribute("item", items);


        return "searchItem/category";
    }

    @GetMapping("/item/{item_id}")
    public String Item(@PathVariable("item_id")Long id, Model model){
        Item item = itemService.findOne(id);
        itemService.updatePopularity(item.getId(), item.getPopularity());

        Item_img item_img = itemService.itemImg(item.getId());
        int cnt = itemService.reviewCnt(item);
        double rate = itemService.reviewRate(item);

        if(item.getReview_cnt() !=cnt )itemService.updateReviewCnt(item.getId(), cnt);

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
    public String ItemOption(@PathVariable("item_id") Long id, @PathVariable("store_id") Long store_id, Model model){
        Item item = itemService.findOne(id);
        Store store = storeService.findById(store_id);
        List<Item_option> item_options = itemService.itemOptionList(item);

        List<StoreQuantity> storeQuantities = new ArrayList<>();
        for (Item_option item_option : item_options){
            storeQuantities.add(itemService.storeQuantityList(item_option, store));
        }


        model.addAttribute("item_option", item_options);
        model.addAttribute("store_Quantity", storeQuantities);
        return "searchItem/item-option";
    }

    @GetMapping("/searchVoice")
    public String searchVoicePage() { return "searchItem/voice-search"; }

    @GetMapping("/searchVoice/{keyword}")
    public String searchVoice(@PathVariable("keyword")String keyword, Model model, @RequestParam(value= "sort", defaultValue = "id")String sort, @RequestParam(value="page", defaultValue = "1") Integer pageNum){



        Page<Item> items = itemService.findByItemName(keyword, pageNum, sort);

        if(items.getTotalElements()==0){
            model.addAttribute("count", items.getTotalElements());
            return "searchItem/search-result";
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("item", items);
        model.addAttribute("count", items.getTotalElements());
        model.addAttribute("maxNum", items.getTotalPages());
        return "searchItem/search-result";
    }

    @GetMapping("/searchItem/{keyword}")
    public String search(@PathVariable("keyword")String keyword, Model model, @RequestParam(value="sort", defaultValue = "id")String sort, @RequestParam(value="page", defaultValue = "1") Integer pageNum){
        if(itemService.findByItemName(keyword, pageNum, sort).getTotalElements()>0) {
            Page<Item> items = itemService.findByItemName(keyword, pageNum, sort);
            List<Item_img> item_imgs = new ArrayList<>();
            for(Item item : items){
                item_imgs.add(itemService.itemImg(item.getId()));
            }
            model.addAttribute("item",itemService.findByBrandName(keyword));
            model.addAttribute("item_img", item_imgs);
            model.addAttribute("item_count", items.getTotalElements());
        }
        if(itemService.findByBrandName(keyword)!=null)model.addAttribute("brand", itemService.findByBrandName(keyword));
        if(itemService.getCategoryByName(keyword)!=null)model.addAttribute("category", itemService.getCategoryByName(keyword));

        return "searchItem/search-result";
    }

    @GetMapping("/review/{item_id}")
    public String review(@PathVariable("item_id")Long id, Model model){
        Item item = itemService.findOne(id);
        List<Review> reviews = itemService.findReviewByItem(item);
        List<Review_img> review_imgs = new ArrayList<>();
        for(Review review : reviews) review_imgs.add(itemService.getReviewImg(review));
        model.addAttribute("item", item);
        model.addAttribute("review", reviews);
        model.addAttribute("review_img", review_imgs);
        return "searchItem/review";
    }

    @GetMapping("/barcode")
    public String barcode(){
        return "barcode/barcode";
    }

    @GetMapping("/popularity")
    public String populart(Model model){
        List<Item> items = itemService.popularity();
        model.addAttribute("item", items);
        return "searchItem/popularity";
    }

}