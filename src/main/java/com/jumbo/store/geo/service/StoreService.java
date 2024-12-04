package com.jumbo.store.geo.service;

import java.util.List;

import com.jumbo.store.geo.model.Store;

public interface StoreService {

    public List<Store> getNearestStores(double latitude, double longitude);
}
