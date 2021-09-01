package com.dayang.miki.Item;

import com.dayang.miki.Item.DTO.ImageDTO;
import com.dayang.miki.Item.DTO.ItemDTO;
import com.dayang.miki.Item.DTO.OptionDTO;
import com.dayang.miki.Item.DTO.ItemPopularDTO;
import com.dayang.miki.Item.reository.DevItemImageRepository;
import com.dayang.miki.Item.reository.DevItemOptionRepository;
import com.dayang.miki.Item.reository.DevItemRepository;
import com.dayang.miki.Item.reository.DevProductImageRepository;
import com.dayang.miki.category.CategoryDTO;
import com.dayang.miki.category.DevCategoryService;
import com.dayang.miki.domain.*;
import com.dayang.miki.store.DevStoreService;
import lombok.RequiredArgsConstructor;
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
    private final DevItemOptionRepository devItemOptionRepository;
    private final DevCategoryService devCategoryService;
    private final DevProductImageRepository devProductImageRepository;
    private final DevStoreService devStoreService;


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
    public ImageDTO imageDTO(Product_img productImg, Item item){
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setImage(productImg.getProduct_img());
        imageDTO.setImageId(productImg.getId());
        imageDTO.setItemId(item.getId());

        return imageDTO;
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

    public Item findById(Long id){
        return devItemRepository.findById(id).get();
    }

    public Item_img findItemImage(Item item){
        return devItemImageRepository.findTop1ByItem(item);
    }

    public ItemDTO findOneItem(Long id){
        Item item = new Item();
        Item_img itemImg = new Item_img();
        try {
            item = findById(id);
            itemImg = findItemImage(item);

        }
        catch (NoSuchElementException e){
            item = null;
            itemImg = null;
        }
        if(item==null) return null;
        ItemDTO itemDTO = itemDTO(item, itemImg);
        return itemDTO;
    }
    public List<ImageDTO> productImage(Long id){
        Item item = findById(id);
        List<Product_img> productImgs = devProductImageRepository.findByItem(item);
        List<ImageDTO> imageDTOList = new ArrayList<>();

        for(Product_img p : productImgs){
            ImageDTO imageDTO = imageDTO(p,item);
            imageDTOList.add(imageDTO);
        }
        return imageDTOList;
    }
    public List<OptionDTO> itemOption(Long itemId, Long storeId){
        Store store = devStoreService.findById(storeId);
        Item item = findById(itemId);
        List<Item_option> item_options = devItemOptionRepository.findByItem(item);
        List<OptionDTO> optionDTOList = new ArrayList<>();

        for(Item_option i : item_options){
            OptionDTO optionDTO = new OptionDTO();
            StoreQuantity storeQuantity = devStoreService.findStoreQuantity(i, store);
            optionDTO.setOptionId(i.getId());
            optionDTO.setOptionName(i.getItem_option_name());
            optionDTO.setTotalCnt(i.getStockQuantity());
            optionDTO.setStoreCnt(storeQuantity.getStock_quantity());
            optionDTOList.add(optionDTO);
        }
        return optionDTOList;
    }

    public List<ItemDTO> searchItem(String keyword, int pageNum, String sort){
        List<Item> itemList = devItemRepository.findByNameContaining(keyword, PageRequest.of(pageNum-1, 9, Sort.by(sort)));
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for(Item item : itemList){
            Item_img item_img = devItemImageRepository.findTop1ByItem(item);
            ItemDTO itemDTO = itemDTO(item, item_img);
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }

    public List<ItemPopularDTO> popularity(){
        List<Item> itemList = devItemRepository.findTop10ByIdGreaterThanOrderByPopularityDesc(1L);
        List<ItemPopularDTO> itemPopularDTOList = new ArrayList<>();

        for(int i=0; i<10; i++){
            ItemPopularDTO itemPopularDTO = new ItemPopularDTO();
            itemPopularDTO.setItemId(itemList.get(i).getId());
            itemPopularDTO.setItemName(itemList.get(i).getName());
            itemPopularDTO.setRank(i+1);
            itemPopularDTOList.add(itemPopularDTO);
        }
        return itemPopularDTOList;
    }
}
