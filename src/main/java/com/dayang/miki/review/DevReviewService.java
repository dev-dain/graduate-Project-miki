package com.dayang.miki.review;

import com.dayang.miki.Item.DevItemService;
import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Review;
import com.dayang.miki.domain.Review_img;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DevReviewService {
    private final DevReviewRepository devReviewRepository;
    private final DevReviewImageRepository devReviewImageRepository;
    private final DevItemService devItemService;

    public ReviewDTO reviewDTO(Review review, Review_img review_img){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(review.getId());
        reviewDTO.setContent(review.getReview_content());
        reviewDTO.setRate(review.getRate());
        reviewDTO.setDate(review.getCreated_at());
        if(review_img==null)reviewDTO.setImage(null);
        else reviewDTO.setImage(review_img.getReview_img());

        return reviewDTO;
    }
    public List<Review> findByItem(Item item){
        return devReviewRepository.findByItem(item);
    }

    public Review_img findReviewImage(Review review){
        return devReviewImageRepository.findByReview(review);
    }

    public List<ReviewDTO> itemReview(Long id){
        Item item = devItemService.findById(id);
        List<Review> reviewList = findByItem(item);
        List<ReviewDTO> reviewDTOList =  new ArrayList<>();
        for(Review review : reviewList){
            Review_img reviewImg = new Review_img();
            try{
                reviewImg = findReviewImage(review);
            }
            catch (NoSuchElementException e){
                reviewImg = null;
            }
            ReviewDTO reviewDTO = reviewDTO(review, reviewImg);
            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }

    public Map<String, Double> reviewSizeRate(Long id){

        Map<String, Double> map = new HashMap<>();
        double rate =0.0;

        Item item = devItemService.findById(id);
        List<Review> reviewList = findByItem(item);

        for(Review review : reviewList) rate += review.getRate();

        map.put("reviewSize", (double) reviewList.size());
        map.put("reviewRate", rate / reviewList.size());
        return map;
    }


}
