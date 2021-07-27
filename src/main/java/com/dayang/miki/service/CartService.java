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
import java.util.Optional;

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
    public List<Cart> findAll(){
        List<Cart> carts;
        carts = cartRepo.findAll();
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
    public List<Item> getItem(){
        List<Item> items= cartRepo.getItem();
        return items;
    }

    @Transactional
    public List<Item_option> getItemOption(){
        List<Item_option> item_options = cartRepo.getItemOption();
        return item_options;
    }
    public Item getSelectItem(Long id){
        Item item = cartRepo.getSelectItem(id);
        return item;
    }
    public Item_option getSelectItemOption(Long id){
        Item_option item_option = cartRepo.getSelectItemOption(id);
        return item_option;
    }

    @Transactional
    public Optional<Cart> findOne(Long id){
        return cartRepo.findById(id);
    }

    @Transactional
    public void updateCartNum(int cnt, Item_option item_option){
        cartRepo.updateCnt(cnt, item_option);
    }

    @Transactional
    public List<Cart> findTestableCart(int pageNum){
        return cartRepo.findTestableCart(PageRequest.of(pageNum-1,4 ));
    }
}
