package com.dayang.miki.repository;

import com.dayang.miki.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemLogicRepository {

    private final EntityManager em;

    //상품 하나 검색
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }
    //카테고리 하나 검색
    public Category findOneCategory(Long id){
        return em.createQuery("select c from Category c where c.id =:id", Category.class)
                .setParameter("id", id)
                .getSingleResult();
    }
    //브랜드로 아이템찾기
    public List<Item> findItemByBrandName(Brand brand){
        return em.createQuery("select i from Item i where i.brand =:brand", Item.class)
                .setParameter("brand", brand)
                .getResultList();
    }

    // 카테고리 아이디 검색
    public List<Item> getByCategory(Long category_id){
        Category category = em.find(Category.class, category_id);
        return category.getItems();
    }
    //상품 옵션
    public List<Item_option> itemOptions(Item item){
        return em.createQuery("select io from Item_option io where io.item =:item", Item_option.class)
                .setParameter("item",item)
                .getResultList();
    }

    //상품 자체 이미지
    public Item_img itemImg(Item item){
        return em.createQuery("select ii from Item_img ii where ii.item =:item", Item_img.class)
                .setParameter("item", item)
                .getSingleResult();
    }

    //상품이미지 리스트
    public List<Product_img> productImgs (Item item){
        return em.createQuery("select po from Product_img po where po.item =:item", Product_img.class)
                .setParameter("item",item)
                .getResultList();
    }

    //상품 리뷰
    public List<Review> reviews (Item item){
        return em.createQuery("select r from Review r where r.item =:item", Review.class)
                .setParameter("item",item)
                .getResultList();
    }
    //상품 리뷰사진 조회
    public Review_img review_imgs(Review review){
        return em.createQuery("select ri from Review_img ri where ri.review =:review", Review_img.class)
                .setParameter("review",review)
                .getSingleResult();
    }
    //검색 시 insert하기
    public void save(Item item){
        if(item.getId()!=null){
            em.persist(item);
        }else{
            em.merge(item);
        }
    }
    //상품 옵션 아이디 가져오기
    public Item_option Single_Item_option(String option_name){
        return em.createQuery("select io from Item_option io where io.item_option_name =:option_name", Item_option.class)
                .setParameter("option_name", option_name)
                .getSingleResult();
    }
    //자기 친구들 카테고리 찾기
    public List<Category>friendCategory(Category category){
        return em.createQuery("select c from Category c where c.parent =:category", Category.class)
                .setParameter("category", category)
                .getResultList();
    }

    //자기 자식 카테고리 찾기
    public List<Category> babyCategory(Category category){
        return em.createQuery("select c.child from Category c where c.parent =:category", Category.class)
                .setParameter("category", category)
                .getResultList();
    }
}
