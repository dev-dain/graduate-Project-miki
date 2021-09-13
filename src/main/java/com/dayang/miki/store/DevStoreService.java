package com.dayang.miki.store;

import com.dayang.miki.domain.Item_option;
import com.dayang.miki.domain.Position;
import com.dayang.miki.domain.Store;
import com.dayang.miki.domain.StoreQuantity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DevStoreService {

    private final DevStoreRepository devStoreRepository;
    private final DevStoreQuantityRepository devStoreQuantityRepository;
    private final DevPositionRepository devPositionRepository;

    public Store findById(Long id){
        return devStoreRepository.findById(id).get();
    }

    public StoreQuantity findStoreQuantity(Item_option item_option, Store store){
        return devStoreQuantityRepository.findByItemOptionAndStore(item_option, store);
    }
    public Position storePosition(Store store){
        return devPositionRepository.findByStore(store);
    }
    public StoreDTO storeDTO(Store store, Double distance, Position position){
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(store.getId());
        storeDTO.setStoreName(store.getStore_name());
        storeDTO.setStoreAddress(store.getAddress());
        storeDTO.setStoreTime("10:00 - 22:00");
        storeDTO.setStoreNumber(store.getNumber());
        storeDTO.setStoreDistance(distance);
        storeDTO.setLatitude(position.getLatitude());
        storeDTO.setLongitude(position.getLongitude());
        return storeDTO;
    }
    public List<Store> findAllStore(){
        return devStoreRepository.findAllByIdGreaterThan(0L);
    }
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    public double distance(double d1, double d2, double p1, double p2){
        double theta = d2 - p2;
        double dist = Math.sin(deg2rad(d1)) * Math.sin(deg2rad(p1)) + Math.cos(deg2rad(d1)) * Math.cos(deg2rad(p1)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return Math.round(dist * 100) / 100.0;
    }

    public List<StoreDTO> nearStore(Long storeId){

        Store store = findById(storeId);
        Position position = storePosition(store);

        List<Store> storeList = findAllStore();
        List<StoreDTO> storeDTOList = new ArrayList<>();

        for(Store s : storeList){
            Position p = storePosition(s);
            double distance = distance(position.getLatitude(), position.getLongitude(), p.getLatitude(), p.getLongitude());
            StoreDTO storeDTO = storeDTO(s, distance, p);
            storeDTOList.add(storeDTO);
        }
        Collections.sort(storeDTOList,
                new Comparator<StoreDTO>() {
                    @Override
                    public int compare(StoreDTO s1, StoreDTO s2) {
                        if( s1.getStoreDistance() < s2.getStoreDistance())  return -1;
                        else if(s1.getStoreDistance() > s2.getStoreDistance()) return 1;
                        return 0;
                    }
                }
        );
        storeDTOList.remove(0);
        return storeDTOList;
    }
}
