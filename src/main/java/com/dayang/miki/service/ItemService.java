package com.dayang.miki.service;


import com.dayang.miki.domain.Item;
import com.dayang.miki.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item findOne(Long itemId){return itemRepository.findOne(itemId); }
    public List<Item> findCategories(String category) { return itemRepository.findCategories(category);}
}
