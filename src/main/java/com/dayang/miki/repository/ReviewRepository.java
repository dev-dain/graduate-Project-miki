package com.dayang.miki.repository;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Review;
import com.dayang.miki.domain.Review_img;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    public List<Review> getReview(Item item){
        return em.createQuery("select r from Review r where r.item =:item", Review.class)
                .setParameter("item", item)
                .getResultList();
    }
    public Review_img getReviewImg(Review review){
        return em.createQuery("select ri from Review_img ri where ri.review =:review", Review_img.class)
                .setParameter("review", review)
                .getSingleResult();
    }
}
