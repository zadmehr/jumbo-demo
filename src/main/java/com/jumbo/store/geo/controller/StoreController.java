package com.jumbo.store.geo.controller;

import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.repository.StoreRepository;
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

    public StoreController(StoreRepository storeRepository, MongoTemplate mongoTemplate) {
        this.storeRepository = storeRepository;
        this.mongoTemplate = mongoTemplate;
    }

    // Rest Api to return list of stores limit 10
    @GetMapping("/stores")
    public List<Store> getStores() {
        return storeRepository.findAll(
        // limit 10
        ).subList(0, 10);

    }

    // Create an api that shows the 5 closest Jumbo stores to a given position.
    @GetMapping("/nearest-stores")
    public List<Store> getNearestStores(@RequestParam double latitude, @RequestParam double longitude) {
        GeoJsonPoint location = new GeoJsonPoint(longitude, latitude);
        System.out.println("Searching nearest stores for location: " + location);

        NearQuery query = NearQuery.near(location)
                .maxDistance(new Distance(100, Metrics.KILOMETERS)) // 100 کیلومتر
                .limit(5);

        var results = mongoTemplate.geoNear(query, Store.class);
        System.out.println("Found results: " + results.getContent().size());

        return results.getContent()
                .stream()
                .map(geoResult -> geoResult.getContent())
                .toList();
    }
    // // curl -X GET "http://localhost:8080/api/v1/nearest-stores?latitude=52.379189&longitude=4.899431"
    //                 http://localhost:8080/api/v1/nearest-stores?latitude=4.615551&longitude=51.778461

}