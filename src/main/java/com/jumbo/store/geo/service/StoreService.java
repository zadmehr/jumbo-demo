package com.jumbo.store.geo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.model.StoreList;
import com.jumbo.store.geo.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        // delete all data and load from json
        // mongoTemplate.dropCollection(Store.class);
        if (storeRepository.count() == 0) {
            loadDataFromJson();
        } else {
            System.out.println("Stores already loaded!");
        }
        createIndex();
    }

    private void loadDataFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/stores.json");

        if (inputStream == null) {
            System.out.println("Unable to find stores.json");
            return;
        }

        try {
            // خواندن فایل JSON و تطابق با StoreList
            TypeReference<StoreList> typeReference = new TypeReference<>() {
            };
            StoreList storeList = mapper.readValue(inputStream, typeReference);

            List<Store> stores = storeList.getStores();
            List<Store> validStores = new ArrayList<>();

            for (Store store : stores) {
                try {
                    double longitude = Double.parseDouble(store.getLongitude() + "");
                    double latitude = Double.parseDouble(store.getLatitude() + "");

                    store.setLongitude(longitude);
                    store.setLatitude(latitude);

                    validStores.add(store);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid coordinates for store: " + store.getUuid());
                }
            }

            // ذخیره داده‌های معتبر
            storeRepository.saveAll(validStores);
            System.out.println("Stores saved successfully!");

        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    private void createIndex() {
        mongoTemplate.indexOps(Store.class)
                .ensureIndex(new GeospatialIndex("location").typed(GeoSpatialIndexType.GEO_2DSPHERE));

        boolean indexExists = mongoTemplate.indexOps(Store.class).getIndexInfo().stream()
                .anyMatch(indexInfo -> indexInfo.getName().equals("location_2dsphere"));

        if (indexExists) {
            System.out.println("Index created successfully!");
        } else {
            System.out.println("Index creation failed!");
        }
    }


    public List<Store> getNearestStores(double latitude, double longitude) {
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
}