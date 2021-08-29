package com.dayang.miki.Item;

import com.dayang.miki.category.CategoryDTO;
import com.dayang.miki.category.DevCategoryService;
import com.dayang.miki.domain.Category;
import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_img;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DevItemService {


    private final DevItemRepository devItemRepository;
    private final DevItemImageRepository devItemImageRepository;
    private final DevCategoryService devCategoryService;


    public Item_img findItemImageByItem(Item item){
        return devItemImageRepository.findTop1ByItem(item);
    }

    public List<ItemDTO> findByCategory(List<CategoryDTO> categories, int pageNum, String sort){
        List<Category> category = new ArrayList<>();
        for(CategoryDTO c: categories) category.add(devCategoryService.findById(c.getCategoryId()));
        List<Item> items =  devItemRepository.findByCategoriesIn(category, PageRequest.of(pageNum-1, 9, Sort.by(sort)));
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
            itemDTOList.add(itemDTO);
        }

        return itemDTOList;
    }
}
