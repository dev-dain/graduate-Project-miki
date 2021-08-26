package com.dayang.miki.Item;

import com.dayang.miki.domain.Category;
import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_img;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DevItemService {


    private final DevItemRepository devItemRepository;
    private final DevItemImageRepository devItemImageRepository;


    public Item_img findItemImageByItem(Item item){
        return devItemImageRepository.findDistinctByItem(item);
    }

    public List<ItemDTO> findByCategory(Category category){

        List<Item> items =  devItemRepository.findByCategories(category);
        List<ItemDTO> itemDTOList = new ArrayList<>();

        for(Item i : items){
            Item_img itemImage = findItemImageByItem(i);

            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setItemId(i.getId());
            itemDTO.setItemName(i.getName());
            itemDTO.setItemPrice(i.getItem_price());
            itemDTO.setItemDiscountPrice(i.getDiscount_price());
            itemDTO.setItemTestable(i.getIs_testable());
            itemDTO.setItemImage(itemImage.getItem_img());
        }

        return itemDTOList;
    }
}
