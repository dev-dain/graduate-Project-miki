package com.dayang.miki.store;

import com.dayang.miki.domain.Item_option;
import com.dayang.miki.domain.Store;
import com.dayang.miki.domain.StoreQuantity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DevStoreService {

    private final DevStoreRepository devStoreRepository;
    private final DevStoreQuantityRepository devStoreQuantityRepository;

    public Store findById(Long id){
        return devStoreRepository.findById(id).get();
    }
    public StoreQuantity findStoreQuantity(Item_option item_option, Store store){
        return devStoreQuantityRepository.findByItemOptionAndStore(item_option, store);
    }
}
