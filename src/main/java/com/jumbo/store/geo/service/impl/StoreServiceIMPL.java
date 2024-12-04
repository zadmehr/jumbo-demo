package com.jumbo.store.geo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.model.StoreList;
import com.jumbo.store.geo.repository.StoreRepository;
import com.jumbo.store.geo.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.NearQuery;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing store-related operations.
 */
@Service
public class StoreServiceIMPL implements StoreService {
    private static final Logger logger = LoggerFactory.getLogger(StoreServiceIMPL.class);

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    // /**
    // * Initializes the service by loading data from JSON if the store repository
    // is empty
    // * and creating the necessary geospatial index.
    // */
    // @PostConstruct
    // public void init() {
    // if (storeRepository.count() == 0) {
    // loadDataFromJson();
    // } else {
    // System.out.println("Stores already loaded!");
    // }
    // createIndex();
    // }

    // /**
    // * Loads store data from a JSON file and saves valid stores to the repository.
    // */
    // private void loadDataFromJson() {
    // ObjectMapper mapper = new ObjectMapper();
    // InputStream inputStream = getClass().getResourceAsStream("/stores.json");

    // if (inputStream == null) {
    // System.out.println("Unable to find stores.json");
    // return;
    // }

    // try {
    // // Read JSON file and map it to StoreList object
    // TypeReference<StoreList> typeReference = new TypeReference<>() {
    // };
    // StoreList storeList = mapper.readValue(inputStream, typeReference);

    // List<Store> stores = storeList.getStores();
    // List<Store> validStores = new ArrayList<>();

    // for (Store store : stores) {
    // try {
    // double longitude = Double.parseDouble(store.getLongitude() + "");
    // double latitude = Double.parseDouble(store.getLatitude() + "");

    // store.setLongitude(longitude);
    // store.setLatitude(latitude);

    // validStores.add(store);
    // } catch (NumberFormatException ex) {
    // System.out.println("Invalid coordinates for store: " + store.getUuid());
    // }
    // }

    // // Save valid data
    // storeRepository.saveAll(validStores);
    // System.out.println("Stores saved successfully!");

    // } catch (IOException e) {
    // System.out.println("Error reading JSON file: " + e.getMessage());
    // }
    // }

    // /**
    // * Creates a geospatial index on the location field of the Store collection.
    // */
    // private void createIndex() {
    // mongoTemplate.indexOps(Store.class)
    // .ensureIndex(new
    // GeospatialIndex("location").typed(GeoSpatialIndexType.GEO_2DSPHERE));

    // boolean indexExists =
    // mongoTemplate.indexOps(Store.class).getIndexInfo().stream()
    // .anyMatch(indexInfo -> indexInfo.getName().equals("location_2dsphere"));

    // if (indexExists) {
    // System.out.println("Index created successfully!");
    // } else {
    // System.out.println("Index creation failed!");
    // }
    // }

    /**
     * Retrieves the nearest stores to the given latitude and longitude.
     *
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @return a list of the nearest stores
     */
    public List<Store> getNearestStores(double latitude, double longitude) {
        logger.info("Fetching nearest stores for latitude: {}, longitude: {}", latitude, longitude);

        GeoJsonPoint location = new GeoJsonPoint(longitude, latitude);
        logger.debug("GeoJsonPoint created: {}", location);

        NearQuery query = NearQuery.near(location)
                .maxDistance(new Distance(100, Metrics.KILOMETERS)) // 100 kilometers
                .limit(5);
        logger.debug("Executing geoNear query: {}", query);
        var results = mongoTemplate.geoNear(query, Store.class);
        logger.info("Found {} stores near the location", results.getContent().size());

        return results.getContent()
                .stream()
                .map(geoResult -> geoResult.getContent())
                .toList();
    }
}