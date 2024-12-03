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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StoreServiceTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() {
        // پاک‌سازی دیتابیس قبل از هر تست
        mongoTemplate.dropCollection(Store.class);

        // ایجاد ایندکس 2dsphere برای فیلد location
        mongoTemplate.indexOps(Store.class)
            .ensureIndex(new GeospatialIndex("location").typed(GeoSpatialIndexType.GEO_2DSPHERE));
    }

    @Test
    public void testGeoQuery() {
        // ایجاد داده تستی
        Store store = new Store();
        store.setUuid("test-uuid");
        store.setCity("Test City");
        store.setLongitude(4.745031);
        store.setLatitude(52.63374);
        mongoTemplate.save(store);

        // اجرای کوئری جغرافیایی
        GeoJsonPoint location = new GeoJsonPoint(4.745031, 52.63374);
        NearQuery query = NearQuery.near(location)
                .maxDistance(new Distance(10, Metrics.KILOMETERS)) // فاصله 10 کیلومتر
                .limit(5);

        var results = mongoTemplate.geoNear(query, Store.class);

        // بررسی نتایج
        assertNotNull(results, "Results should not be null");
        assertFalse(results.getContent().isEmpty(), "No stores found!");
        assertEquals("Test City", results.getContent().get(0).getContent().getCity(), "City name mismatch!");
    }
}