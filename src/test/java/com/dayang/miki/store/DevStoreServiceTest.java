package com.dayang.miki.store;

import com.dayang.miki.domain.Store;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class DevStoreServiceTest {


    @Autowired
    DevStoreService devStoreService;

    @Test
    void findAllStore() {
        //given
        List<Store> storeList = devStoreService.findAllStore();

        //then
        assertThat(storeList.size()).isEqualTo(35);
    }

    @Test
    void nearStore(){
        //given
        Long storeId = 1L;
        //when
        List<StoreDTO> storeDTOList = devStoreService.nearStore(storeId);
        //then
        assertThat(storeDTOList.size()).isEqualTo(34);
    }
}