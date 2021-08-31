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
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DevItemService {


    private final DevItemRepository devItemRepository;
    private final DevItemImageRepository devItemImageRepository;
    private final DevCategoryService devCategoryService;


    public Item_img findItemImageByItem(Item item){
        return devItemImageRepository.findTop1ByItem(item);
    }

    public ItemDTO itemDTO(Item item, Item_img itemImg){

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemId(item.getId());
        itemDTO.setItemName(item.getName());
        itemDTO.setItemPrice(item.getItem_price());
        itemDTO.setItemDiscountPrice(item.getDiscount_price());
        itemDTO.setItemTestable(item.getIs_testable());
        itemDTO.setItemImage(itemImg.getItem_img());

        return itemDTO;
    }

    public List<ItemDTO> findByCategory(List<CategoryDTO> categories, int pageNum, String sort){
        List<Category> category = new ArrayList<>();
        for(CategoryDTO c: categories) category.add(devCategoryService.findById(c.getCategoryId()));
        List<Item> items =  devItemRepository.findByCategoriesIn(category, PageRequest.of(pageNum-1, 9, Sort.by(sort)));
        List<ItemDTO> itemDTOList = new ArrayList<>();

        for(Item i : items){
            Item_img itemImage = findItemImageByItem(i);
            ItemDTO itemDTO = itemDTO(i, itemImage);
            itemDTOList.add(itemDTO);
        }

        return itemDTOList;
    }
    public ItemDTO findById(Long id){
        Item item = new Item();
        Item_img itemImg = new Item_img();
        try {
            item = (devItemRepository.findById(id)).get();
            itemImg = devItemImageRepository.findTop1ByItem(item);

        }
        catch (NoSuchElementException e){
            item = null;
            itemImg = null;
        }
        if(item==null) return null;
        ItemDTO itemDTO = itemDTO(item, itemImg);
        return itemDTO;
    }
}
