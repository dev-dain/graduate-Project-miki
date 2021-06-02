package com.dayang.miki.service;


import com.dayang.miki.domain.Cart;

import com.dayang.miki.repository.CartRepo;
import com.dayang.miki.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public List<Cart> findAll(){
        List<Cart> carts = new ArrayList<>();
        carts = cartRepository.findAll();
        return carts;
    }
    @Transactional
    public void truncateCart() {
        cartRepo.truncateCart();
    }
    @Transactional
    public void validate(Cart cart){
       try{
            Cart c = cartRepository.findOne(cart.getItem());
            cartRepo.updateCnt(c.getCount(), c.getId());
        }
        catch(EmptyResultDataAccessException e){
            cartRepository.save(cart);
        }
    }
}
