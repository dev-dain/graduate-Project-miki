package com.dayang.miki.store;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreDTO {
    private Long storeId;
    private String storeName;
    private String storeAddress;
    private String storeTime;
    private String storeNumber;
    private Double storeDistance;
    private Double latitude;
    private Double longitude;

}
