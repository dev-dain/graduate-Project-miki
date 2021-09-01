package com.dayang.miki.store;

import com.dayang.miki.domain.Item_option;
import com.dayang.miki.domain.Store;
import com.dayang.miki.domain.StoreQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevStoreQuantityRepository extends JpaRepository <StoreQuantity, Long> {
   StoreQuantity findByItemOptionAndStore(Item_option item_option, Store store);

}
