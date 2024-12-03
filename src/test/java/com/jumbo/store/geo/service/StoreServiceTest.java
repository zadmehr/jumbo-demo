package com.jumbo.store.geo.service;

import com.jumbo.store.geo.model.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// @ActiveProfiles("test")
public class StoreServiceTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() {
        // Clean Database before each test
        // mongoTemplate.dropCollection(Store.class);

        // Create Index for GeoSpatial Query
        // mongoTemplate.indexOps(Store.class)
        // .ensureIndex(new
        // GeospatialIndex("location").typed(GeoSpatialIndexType.GEO_2DSPHERE));
    }

    // Test if there is any data in the database
    @Test
    public void testDatabase() {
        var stores = mongoTemplate.findAll(Store.class);
        assertNotNull(stores, "Test Faild: Stores should not be null");
        assertFalse(stores.isEmpty(), "Test Faild: No stores found!");
    }

    // Test check index
    @Test
    public void testIndex() {
        var indexInfo = mongoTemplate.indexOps(Store.class).getIndexInfo();
        assertNotNull(indexInfo, "Test Faild: Index should not be null");
        assertFalse(indexInfo.isEmpty(), "Test Faild: No index found!");
    }

    // Test Geo Query
    @Test
    public void testGeoQuery() {

        // Execute Geo Query
        GeoJsonPoint location = new GeoJsonPoint(4.883832, 52.37867);
        NearQuery query = NearQuery.near(location)
                .maxDistance(new Distance(1000, Metrics.KILOMETERS))
                .limit(5);

        var results = mongoTemplate.geoNear(query, Store.class);

        // Evaluate test results
        assertNotNull(results, "Test Faild: Results should not be null");
        assertFalse(results.getContent().isEmpty(), "Test Faild: No stores found!");
    }
}