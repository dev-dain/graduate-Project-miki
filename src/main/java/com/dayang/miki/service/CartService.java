package com.dayang.miki.service;


import com.dayang.miki.domain.Cart;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_option;
import com.dayang.miki.repository.CartRepo;
import com.dayang.miki.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartRepo cartRepo;

    @Transactional
    public Long save(Cart cart){
        cartRepository.save(cart);
        return cart.getId();
    }
    @Transactional
    public void deleteOne(Long basket_id){
        cartRepo.deleteById(basket_id);
    }
    @Transactional
    public Page<Cart> findAll(Integer pageNum){
        Page<Cart> carts;
        carts = cartRepo.findAll(PageRequest.of(pageNum-1, 4, Sort.by("item")));
        return carts;
    }
    @Transactional
    public void truncateCart() {
        cartRepo.truncateCart();
    }

    @Transactional
    public void validate(Cart cart){
       try{
            Cart c = cartRepository.findOne(cart.getItem_option());
            cartRepo.updateCnt(c.getCount() + cart.getCount(), c.getItem_option());
        }
        catch(EmptyResultDataAccessException e){
            cartRepository.save(cart);
        }
    }

    @Transactional
    public List<Item> getItem(Integer pageNum){
        List<Item> items= cartRepo.getItem(PageRequest.of(pageNum-1, 4));
        return items;
    }

    @Transactional
    public List<Item_option> getItemOption(Integer pageNum){
        List<Item_option> item_options = cartRepo.getItemOption(PageRequest.of(pageNum-1, 4));
        return item_options;
    }

    @Transactional
    public List<Item> getItemNoPage(){
        List<Item> items = cartRepo.getItemNoPage();
        return items;
    }
}
