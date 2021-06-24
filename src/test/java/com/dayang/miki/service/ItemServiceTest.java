package com.dayang.miki.service;

import com.dayang.miki.domain.*;
import com.dayang.miki.repository.BrandRepository;
import com.dayang.miki.repository.CategoryRepository;
import com.dayang.miki.repository.ItemLogicRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@Transactional
class ItemServiceTest {
    @Autowired
    ItemService itemService;
    @Autowired
    EntityManager em;
    @Autowired
    ItemLogicRepository itemLogicRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    StoreService storeService;

    @Test
    @Rollback(value = false)
    void testItem(){
       Category category = itemService.findOneCategory(49L);
       List<Item>items = itemService.getByCategory(category.getId());
       for(Item item : items){
           //System.out.println(item.getId()+" "+item.getName());
           itemService.update(item.getId());
       }
    }

    @Test
    void positionDist(){
        Long id = 1L;
        Store store = storeService.findById(id);
        Position position = storeService.findSinglePosition(store);

        List<Position> positions = storeService.findAllPosition();
        List<Double> dist = new ArrayList<>();
        for(Position position1 : positions){
            dist.add(storeService.positionDist(position.getLatitude(), position1.getLatitude(),
                    position.getLongitude(), position1.getLongitude()));
        }
        for(Double d : dist){
            System.out.println(d);
        }
    }


    @Test
    void imgTest(){
        Long id = 47L;
        Category category = itemService.findOneCategory(id);
        List<Item> items = itemService.getByCategory(category.getId());
        for(Item item : items){
            System.out.println(item.getId() +" " + item.getName());
        }
    }


    @Test
    void voiceSearch(){
        String keyword = "삐아";
        int pageNum = 1;
        Page<Item> items = itemService.findByItemName(keyword, 3);
        for(Item item : items){
            System.out.println(item.getName());
        }
         System.out.println(items.getTotalElements());
        System.out.println(items.getTotalPages());
    }

/*    @Test
    @Rollback(false)
    void change(){
        Long category_id = 36L;
        List<Item> items = itemService.getByCategory(category_id);
        for(Item item : items){
            itemService.updateTestable(item.getId());
        }
    }*/

    @Test
    void pagingItem(){
        Long category_id = 36L;
        int pageNum = 1;
        Category category = itemService.findOneCategory(category_id);
        List<Category> categories = itemService.getFriendCategory(category);
        categories.add(category);
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
        System.out.println("===================================================");
        System.out.println("그다음 친구들");

        for(Category c : categories){
            System.out.println(c.getId());

        }

        System.out.println("===================================================");
        System.out.println(itemService.getItemNum(showCategory));
        System.out.println(itemService.getItemNum(showCategory)/4);
    }


    @Test
    void items(){
        Long id = 41L;
        Category category = itemService.findOneCategory(id);
        List<Category> categories = itemService.getFriendCategory(category);
        List<Item> items = itemService.getByCategory(id);
        for(Item i : items){
            System.out.println("item_name = " + i.getName()+" item_id = "+ i.getId());
        }
    }

    @Test
    void findByName(){

        //given
        String name = "ddd";
        //when
        Page<Item> items = itemService.findByItemName(name, 1);
        //then
        for(Item item : items){
            System.out.println(item.getName());
        }

    }

    @Test
    void option_List(){

        //given
        Long item_id = 1L;
        Item item = itemService.findOne(item_id);
        //when
        List<Item_option> item_option = itemService.itemOptionList(item);
        for(Item_option item_options : item_option){
            System.out.println("item_option = " +item_options.getItem_option_name());
        }
        //then
    }

    @Test
    void Product_Img_List(){

        //given
        Long item_id = 4L;
        Item item = itemService.findOne(item_id);
        //when
        List<Product_img> product_imgs = itemService.productImgs(item);
        for(Product_img product_img : product_imgs){
            System.out.println("product_img = " +product_img.getProduct_img());
        }
        //then
    }

    @Test
    void ReviewCnt(){
        //given
        Long item_id = 1L;
        Item item = itemService.findOne(item_id);
        List<Review> reviews = itemService.reviews(item);
        //when
        int cnt = itemService.reviewCnt(item);
        //then
        Assertions.assertThat(cnt).isEqualTo(reviews.size());
    }

    @Test
    void ReviewRate(){
        //given
        Long item_id = 1L;
        Item item = itemService.findOne(item_id);
        List<Review> reviews = itemService.reviews(item);
        //when
        float rate = itemService.reviewRate(item);
        //then

        Assertions.assertThat(rate).isEqualTo(3.1f);
    }

    @Test
    void Review(){
        //given
        Long item_id = 1L;
        Item item = itemService.findOne(item_id);
        List<Review> reviews = itemService.reviews(item);
        //when
        for(Review review : reviews){
            System.out.println(review.getCreated_at().getYear()+" "+review.getCreated_at().getMonth()+" "+review.getCreated_at().getDayOfWeek()+ "    " +review.getReview_content());
        }
    }

    @Test
    void ReviewImg(){
        //given
        Long item_id=1L;
        Item item = itemService.findOne(item_id);

        //when
        List<Review_img> ri = itemService.reviewImgs(item);
        for(Review_img review_img : ri){
            System.out.println(review_img.getReview_img() + " " + review_img.getId());
        }
    }

    @Test
    void findCategoryByName(){
        String name = "베이스메이크업";
        List<Category> id = categoryRepository.findByNameContaining(name);
        for(Category i : id){
            System.out.println(i.getId());
        }

    }
    @Test
    void findItemByBrandName(){
        String name = "ㄷㄱ";
        List<Item> items = itemService.findItemByBrandName(name);
        for(Item item: items){
            System.out.println(item.getName());
        }
    }
    @Test
    void findBrandByName(){
        String name = "ㄷㄱ";
        Brand brand = itemService.findByBrandName(name);

        System.out.println(brand.getId());
    }
    @Test
    void reviewTest(){
        Item item = itemService.findOne(338L);
        List<Review> reviews = itemService.findReviewByItem(item);
        List<Review_img> review_imgs = new ArrayList<>();
        for(Review review : reviews){
                review_imgs.add(itemService.getReviewImg(review));
           System.out.println(review.getReview_content());
       }
       for(Review_img review_img: review_imgs) {
           if(review_img==null) System.out.println("null");
          else System.out.println(review_img.getReview_img());
       }

    }
}