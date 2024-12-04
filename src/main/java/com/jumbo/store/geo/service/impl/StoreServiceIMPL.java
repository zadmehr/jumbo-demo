package com.jumbo.store.geo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.repository.StoreRepository;
import com.jumbo.store.geo.service.StoreService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Implementation of StoreService for managing store-related operations.
 */
@Service
public class StoreServiceIMPL implements StoreService {
    private static final Logger logger = LoggerFactory.getLogger(StoreServiceIMPL.class);
    private static final double MAX_DISTANCE_KM = 100.0;
    private final StoreRepository storeRepository;

    public StoreServiceIMPL(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> getNearestStores(String latitude, String longitude) {
        logger.info("Fetching nearest stores for latitude: {}, longitude: {}", latitude, longitude);

        // Validate and parse latitude and longitude
        double lat = parseCoordinate(latitude, "latitude");
        double lon = parseCoordinate(longitude, "longitude");

        Point location = new Point(lon, lat);
        logger.debug("Point created: {}", location);

        Distance distance = new Distance(MAX_DISTANCE_KM, Metrics.KILOMETERS);
        logger.debug("Searching for stores within distance: {}", distance);

        Pageable limit = PageRequest.of(0, 5);
        return storeRepository.findByLocationNear(location, distance, limit);
    }

    /**
     * Parses a coordinate string into a double and validates its range.
     *
     * @param coordinate The coordinate value as a string.
     * @param type       The type of coordinate ("latitude" or "longitude") for
     *                   error messages.
     * @return The parsed double value.
     * @throws IllegalArgumentException if the coordinate is invalid or out of
     *                                  range.
     */
    private double parseCoordinate(String coordinate, String type) {
        try {
            double value = Double.parseDouble(coordinate);
            if ("latitude".equals(type) && (value < -90 || value > 90)) {
                throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees.");
            }
            if ("longitude".equals(type) && (value < -180 || value > 180)) {
                throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees.");
            }
            return value;
        } catch (NumberFormatException e) {
            logger.error("Invalid {} value: {}", type, coordinate);
            throw new IllegalArgumentException("Invalid " + type + " value: " + coordinate, e);
        }
    }
}