package com.jumbo.store.geo.controller.dto;

import com.jumbo.store.geo.model.Store;

public record StoreDTO(String name, double latitude, double longitude) {

    public static StoreDTO fromStore(Store store) {
        return new StoreDTO(store.getCity(), store.getLatitude(), store.getLongitude());
    }
}