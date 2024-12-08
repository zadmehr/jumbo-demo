package com.jumbo.store.geo.service.impl;

import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoreServiceImplTest {

    private final StoreRepository storeRepository = mock(StoreRepository.class);
    private final StoreServiceImpl storeService = new StoreServiceImpl(storeRepository);

    @Test
    void testGetNearestStores_ValidCoordinates() {
        // Arrange
        String latitude = "52.37867";
        String longitude = "4.8838";
        List<Store> mockStores = List.of(new Store(), new Store());
        when(storeRepository.findByLocationNear(any(Point.class), any(Distance.class), any(PageRequest.class)))
            .thenReturn(mockStores);

        // Act
        List<Store> stores = storeService.getNearestStores(latitude, longitude);

        // Assert
        assertEquals(2, stores.size());
        verify(storeRepository, times(1))
            .findByLocationNear(any(Point.class), any(Distance.class), any(PageRequest.class));
    }

    @Test
    void testGetNearestStores_InvalidLatitude() {
        String latitude = "100"; // Invalid latitude
        String longitude = "4.8838";

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> storeService.getNearestStores(latitude, longitude)
        );

        assertEquals("Latitude must be between -90 and 90 degrees.", exception.getMessage());
    }

    @Test
    void testGetNearestStores_InvalidLongitude() {
        String latitude = "52.37867";
        String longitude = "200"; // Invalid longitude

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> storeService.getNearestStores(latitude, longitude)
        );

        assertEquals("Longitude must be between -180 and 180 degrees.", exception.getMessage());
    }
}