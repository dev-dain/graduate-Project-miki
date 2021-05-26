package com.dayang.miki.service;

import com.dayang.miki.domain.Position;
import com.dayang.miki.domain.Store;
import com.dayang.miki.repository.PositionRepository;
import com.dayang.miki.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final PositionRepository positionRepository;
    @Autowired
    private final LocationDistance locationDistance;

    @Transactional
    public Store findById(Long id){
        Store store = storeRepository.findById(id);
        return store;
    }
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

    public double positionDist(double lat1, double lat2, double lng1, double lng2){
        double position = locationDistance.distance(lat1, lng1, lat2, lng2);
        return position;
    }

    public Optional<Position> findPositionById(Store store){
        Optional<Position> position = positionRepository.findByStore(store);
        return position;
    }
}
