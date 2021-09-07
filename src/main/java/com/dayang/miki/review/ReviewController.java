package com.dayang.miki.review;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dev")
@RequiredArgsConstructor
public class ReviewController {

    private final DevReviewService devReviewService;

    @GetMapping("/item/{itemId}/review")
    public JSONObject Review(@PathVariable("itemId")Long id){
        JSONObject jsonObject = new JSONObject();
        List<ReviewDTO> reviewDTOList = devReviewService.itemReview(id);
        jsonObject.put("Review", reviewDTOList);
        return jsonObject;
    }

    @GetMapping("/item/{itemId}/reviewCnt")
    public JSONObject ReviewCnt(@PathVariable("itemId")String id){
        JSONObject jsonObject = new JSONObject();

        Map<String, Double> map = devReviewService.reviewSizeRate(Long.parseLong(id));

        int size = (int) Math.round((map.get("size")));
        double rate = map.get("rate");

        jsonObject.put("size", size);
        jsonObject.put("rate", rate);

        return jsonObject;
    }
}
