package com.dayang.miki.service;

import com.dayang.miki.domain.Basket;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_option;
import com.dayang.miki.repository.BasketRepo;
import com.dayang.miki.repository.BasketRepository;
import com.dayang.miki.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketRepo basketRepo;
    @Transactional
    public Long save(Basket basket){
        basketRepository.save(basket);
        return basket.getId();
    }
    @Transactional
    public void deleteOne(Long basket_id){
        basketRepo.deleteById(basket_id);
    }
    @Transactional
    public List<Basket> findAll(){
        return basketRepository.findAll();
    }
    @Transactional
    public void truncateBasket() {
        basketRepo.truncateBasket();
    }
    @Transactional
    public void validate(Basket basket){
       try{
            Basket b = basketRepository.findOne(basket.getItem());
            basketRepo.updateCnt(b.getCount(), b.getId());
        }
        catch(EmptyResultDataAccessException e){
            basketRepository.save(basket);
        }
    }
}
