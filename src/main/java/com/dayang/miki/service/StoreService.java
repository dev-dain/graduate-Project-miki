package com.dayang.miki.service;

import com.dayang.miki.domain.Position;
import com.dayang.miki.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
    private final StoreService storeService;
    @Transactional
    public Store findSingleStore(String name){
        Store store = storeService.findSingleStore(name);
        return store;
    }
    @Transactional
    public List<Store> findAllStore(){
        List<Store> stores = storeService.findAllStore();
        return stores;
    }
    @Transactional
    public Position findSinglePosition(Store store){
        Position position = storeService.findSinglePosition(store);
        return position;
    }
    @Transactional
    public List<Position> findAllPosition(){
        List<Position> positions= storeService.findAllPosition();
        return positions;
    }
}
