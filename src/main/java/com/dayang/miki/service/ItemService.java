package com.dayang.miki.service;

import com.dayang.miki.domain.*;
import com.dayang.miki.repository.*;
import com.dayang.miki.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemLogicRepository itemLogicRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepo;
    private final BrandRepository brandRepository;
    private final HistoryRepository historyRepository;

    @Transactional
    public void save (Item item){
        History history = History.createHistory(item);
        historyRepository.save(history);
    }
    @Transactional
    public Item findOne (Long item_id){return itemLogicRepository.findOne(item_id);}

    @Transactional
    public List<Item> findByItemName (String name){
        List<Item> items = itemRepo.findByNameContaining(name);
        List<Item> item_List = new ArrayList<>();

        if (items.isEmpty()) return item_List;

        for (Item item : items) {
            item_List.add(item);
        }
        return item_List;
    }

    @Transactional
    public Brand findByBrandName(String name){
        return brandRepository.findByNameContaining(name);

    }
    @Transactional
    public List<Item> findItemByBrandName(String name){
        Brand brand = brandRepository.findByNameContaining(name);
        List<Item> items = itemLogicRepository.findItemByBrandName(brand);
        return items;
    }

    @Transactional
    public List<Category> getCategoryByName(String name){return categoryRepository.findByNameContaining(name);}
    @Transactional
    public List<Item> categoryItemList(Long id){return itemLogicRepository.getByCategory(id);}
    @Transactional
    public List<Item_option> itemOptionList(Item item){return itemLogicRepository.itemOptions(item);}
    @Transactional
    public Item_img itemImg(Item item){return itemLogicRepository.itemImg(item);}
    @Transactional
    public List<Product_img> productImgs(Item item){ return itemLogicRepository.productImgs(item);}
    @Transactional
    public List<Review> reviews (Item item){return itemLogicRepository.reviews(item);}
    @Transactional
    public int reviewCnt(Item item){return itemLogicRepository.reviews(item).size();}
    @Transactional
    public float reviewRate(Item item){
        List<Review> reviews = itemLogicRepository.reviews(item);
        int cnt = reviews.size();
        float sum=0;
        for(Review review : reviews){
            sum+=review.getRate();
        }
        return sum/cnt;
    }
    @Transactional
    public List<Review_img> reviewImgs(Item item){
        List<Review> reviews = itemLogicRepository.reviews(item);
        List<Review_img> review_imgs = new ArrayList<>();
        for(Review review : reviews){
           try {
               Review_img ri = itemLogicRepository.review_imgs(review);
               review_imgs.add(ri);
           } catch(EmptyResultDataAccessException e){
               continue;
           }
        }
        return review_imgs;
    }
    @Transactional
    public Item_option Single_Item_option(String name){
        return itemLogicRepository.Single_Item_option(name);
    }

/*
    @Transactional
    public
*/

}
