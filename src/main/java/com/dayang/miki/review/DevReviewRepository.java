package com.dayang.miki.review;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByItem(Item item);
}
