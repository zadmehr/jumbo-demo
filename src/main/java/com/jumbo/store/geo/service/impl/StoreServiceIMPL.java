package com.jumbo.store.geo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.repository.StoreRepository;
import com.jumbo.store.geo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.NearQuery;
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