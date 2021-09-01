package com.dayang.miki.review;

import com.dayang.miki.domain.Review;
import com.dayang.miki.domain.Review_img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevReviewImageRepository extends JpaRepository<Review_img, Long> {
    Review_img findByReview(Review review);
}
