package com.dayang.miki.service;

import com.dayang.miki.domain.*;
import com.dayang.miki.repository.*;
import com.dayang.miki.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.boot.archive.scan.spi.PackageInfoArchiveEntryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemLogicRepository itemLogicRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepo;
    private final BrandRepository brandRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewRepo reviewRepo;



    @Transactional
    public void update(Long id){
        itemRepo.updateTestable(id);
    }
    @Transactional
    public void updateStockQuantity(int stockQuantity, Long item_option_id){
        itemOptionRepository.updateStockQuantity(stockQuantity, item_option_id);
    }


    @Transactional
    public Item findOne (Long item_id){return itemLogicRepository.findOne(item_id);}

    @Transactional
    public Category findOneCategory(Long category_id){
        Category category = itemLogicRepository.findOneCategory(category_id);
        return category;
    }
    @Transactional
    public Page<Item> findByItemName (String name, Integer pageNum, String sort){
        Page<Item> items = itemRepo.findByNameContaining(name, PageRequest.of(pageNum-1, 9, Sort.by(sort)));

        return items;
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
    public long getItemNum(List<Category> ids){
        long count = itemRepo.countItemNum(ids);
        if (count%9==0) return count/9;
        else return (count/9)+1;
    }
    @Transactional
    public List<Item> popularity(){
        return itemRepo.popularity();
    }
    @Transactional
    public List<Item> findItemByCategory(List<Category> categoryids, Integer pageNum, String sort){

        List<Item> items = itemRepo.findItemByIn(categoryids, PageRequest.of(pageNum-1, 9, Sort.by(sort)));

        return items;
    }
    @Transactional
    public List<Category> getFriendCategory(Category category){
        List<Category> categories = itemLogicRepository.friendCategory(category);
        return categories;
    }
    @Transactional
    public List<Item> getByCategory(Long category_id) {
        return itemLogicRepository.getByCategory(category_id);
    }
    @Transactional
    public List<Item_option> itemOptionList(Item item){return itemLogicRepository.itemOptions(item);}
    @Transactional
    public Item_img itemImg(Long id){return itemImgRepository.itemImg(id);}
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

    @Transactional
    public void updateReviewCnt(Long id, int reviewCnt){
        itemRepo.updateReview(reviewCnt, id);
    }

    @Transactional
    public void updateOrderCnt(Long id, int orderCnt){
        itemRepo.updateOrderCnt(orderCnt, id);
    }
    @Transactional
    public void updatePopularity(Long id, int popularity){
        itemRepo.updatePopularity(popularity + 1, id);
    }
    @Transactional
    public StoreQuantity storeQuantityList(Item_option item_option, Store store){
        return itemLogicRepository.storeQuantityList(item_option, store);
    }
    @Transactional
    public Item_option findItemOptionById(Long id){
        return itemLogicRepository.findItemOptionById(id);
    }

    @Transactional
    public List<Review> getReviewList(Item item){return reviewRepository.getReview(item);}

    @Transactional
    public List<Review> findReviewByItem(Item item){
        return reviewRepo.findByItem(item);
    }

    @Transactional
    public Review_img getReviewImg(Review review){
        Review_img review_img;
        try {
            review_img = reviewRepository.getReviewImg(review);
        }catch (EmptyResultDataAccessException e){
            review_img = new Review_img();
        }
        return review_img;
    }

    @Transactional
    public List<Item_img> getCartImg(List<Item> items){
        List<Item_img> item_imgs = itemImgRepository.itemImgcart(items);
        return item_imgs;
    }

/*    @Transactional
    public void updateTestable(Long id){
        itemRepo.updateShowCount(id);
    }*/
}
