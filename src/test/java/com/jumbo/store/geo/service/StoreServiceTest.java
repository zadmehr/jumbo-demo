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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class StoreServiceTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Store.class);

        // Add sample data for testing
        Store store1 = new Store();
        store1.setUuid("test-store-1");
        store1.setCity("Amsterdam");
        store1.setLongitude(4.883832);
        store1.setLatitude(52.37867);

        Store store2 = new Store();
        store2.setUuid("test-store-2");
        store2.setCity("Haarlem");
        store2.setLongitude(4.64622);
        store2.setLatitude(52.38739);

        mongoTemplate.save(store1);
        mongoTemplate.save(store2);

        // Create 2dsphere index
        mongoTemplate.indexOps(Store.class)
                .ensureIndex(new GeospatialIndex("location").typed(GeoSpatialIndexType.GEO_2DSPHERE));

    }

    // @Test
    // void testDatabase() {
    //     List<Store> stores = mongoTemplate.findAll(Store.class);
    //     assertNotNull(stores, "Stores should not be null");
    //     assertEquals(2, stores.size(), "Expected exactly 2 stores");
    // }

    // @Test
    // void testGeoQuery() {
    //     GeoJsonPoint location = new GeoJsonPoint(4.883832, 52.37867); // Amsterdam
    //     NearQuery query = NearQuery.near(location)
    //             .maxDistance(new Distance(50, Metrics.KILOMETERS))
    //             .limit(5);

    //     var results = mongoTemplate.geoNear(query, Store.class);

    //     assertNotNull(results, "Results should not be null");
    //     assertEquals(2, results.getContent().size(), "Expected 2 nearby stores");
    //     assertEquals("Amsterdam", results.getContent().get(0).getContent().getCity(), "First store should be Amsterdam");
    // }

    // @Test
    // void testGeoIndex() {
    //     var indexInfo = mongoTemplate.indexOps(Store.class).getIndexInfo();
    //     assertTrue(indexInfo.stream().anyMatch(index -> index.getName().equals("location_2dsphere")),
    //             "2dsphere index should exist");
    // }
}