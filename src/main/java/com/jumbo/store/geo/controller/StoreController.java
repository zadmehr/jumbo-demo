package com.jumbo.store.geo.controller;

import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.repository.StoreRepository;
import com.jumbo.store.geo.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// add prefix /api/v1
@RequestMapping("/api/v1")
public class StoreController {

    @Autowired
    private final StoreRepository storeRepository;

    @Autowired
    private final MongoTemplate mongoTemplate;

    @Autowired
    private final StoreService storeService;

    public StoreController(StoreRepository storeRepository, MongoTemplate mongoTemplate, StoreService storeService) {
        this.storeRepository = storeRepository;
        this.mongoTemplate = mongoTemplate;
        this.storeService = storeService;
    }

    // Create an api that shows the 5 closest Jumbo stores to a given position.
    @GetMapping("/nearest-stores")
    public List<Store> getNearestStores(@RequestParam double latitude, @RequestParam double longitude) {
        return storeService.getNearestStores(latitude, longitude);
    }

}