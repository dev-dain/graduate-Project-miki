package com.dayang.miki.service;


import com.dayang.miki.domain.Cart;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_option;
import com.dayang.miki.repository.CartReposit;
import com.dayang.miki.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartReposit cartRepository;
    private final CartRepository cartReposit;

    @Transactional
    public Long save(Cart cart){
        cartRepository.save(cart);
        return cart.getId();
    }
    @Transactional
    public void deleteOne(Long basket_id){
        cartReposit.deleteById(basket_id);
    }
    @Transactional
    public List<Cart> findAll(){
        List<Cart> carts;
        carts = cartReposit.findAll();
        return carts;
    }
    @Transactional
    public void truncateCart() {
        cartReposit.truncateCart();
    }

    @Transactional
    public void validate(Cart cart){
       try{
            Cart c = cartRepository.findOne(cart.getItem_option());
            cartReposit.updateCnt(c.getCount() + cart.getCount(), c.getItem_option());
        }
        catch(EmptyResultDataAccessException e){
            cartRepository.save(cart);
        }
    }

    @Transactional
    public List<Item> getItem(){
        List<Item> items= cartReposit.getItem();
        return items;
    }

    @Transactional
    public List<Item_option> getItemOption(){
        List<Item_option> item_options = cartReposit.getItemOption();
        return item_options;
    }
    public Item getSelectItem(Long id){
        Item item = cartReposit.getSelectItem(id);
        return item;
    }
    public Item_option getSelectItemOption(Long id){
        Item_option item_option = cartReposit.getSelectItemOption(id);
        return item_option;
    }

    @Transactional
    public Cart findOne(Long cart_id){
        return cartReposit.cartone(cart_id);
    }

    @Transactional
    public void updateCartNum(int cnt, Item_option item_option){
        cartReposit.updateCnt(cnt, item_option);
    }

    @Transactional
    public List<Cart> findTestableCart(int pageNum){
        return cartReposit.findTestableCart(PageRequest.of(pageNum-1,4 ));
    }
}
