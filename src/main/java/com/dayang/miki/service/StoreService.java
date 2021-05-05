package com.dayang.miki.service;

import com.dayang.miki.domain.Position;
import com.dayang.miki.domain.Store;
import com.dayang.miki.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public Store findSingleStore(String name){
        Store store = storeRepository.getSingleStore(name);
        return store;
    }
    @Transactional
    public List<Store> findAllStore(){
        List<Store> stores = storeRepository.getAllStore();
        return stores;
    }
    @Transactional
    public Position findSinglePosition(Store store){
        Position position = storeRepository.getSinglePosition(store);
        return position;
    }
    @Transactional
    public List<Position> findAllPosition(){
        List<Position> positions= storeRepository.getAllPosition();
        return positions;
    }
}
