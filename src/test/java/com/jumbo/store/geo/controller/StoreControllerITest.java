package com.jumbo.store.geo.controller;

import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Uses the "test" profile for the test configuration
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StoreRepository storeRepository;

    @BeforeEach
    void setUp() {
        // Clear the database before each test
        storeRepository.deleteAll();

        // Insert test data
        Store store1 = Store.builder()
                .city("Amsterdam")
                .postalCode("1012AB")
                .street("Damstraat")
                .addressName("Store A")
                .longitude(4.8952)
                .latitude(52.3702)
                .todayOpen("08:00")
                .todayClose("22:00")
                .collectionPoint(true)
                .complexNumber("123")
                .build();

        Store store2 = Store.builder()
                .city("Rotterdam")
                .postalCode("3012AB")
                .street("Coolsingel")
                .addressName("Store B")
                .longitude(4.47917)
                .latitude(51.9225)
                .todayOpen("08:00")
                .todayClose("22:00")
                .collectionPoint(true)
                .complexNumber("456")
                .build();

        storeRepository.saveAll(List.of(store1, store2));
    }

    @Test
    void getNearestStores_ValidRequest_ReturnsStores() throws Exception {
        mockMvc.perform(get("/api/v1/nearest-stores")
                .param("latitude", "52.3702")
                .param("longitude", "4.8952")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].addressName", is("Store A")))
                .andExpect(jsonPath("$[0].city", is("Amsterdam")))
                .andExpect(jsonPath("$[1].addressName", is("Store B")))
                .andExpect(jsonPath("$[1].city", is("Rotterdam")));
    }

    @Test
    void getNearestStores_InvalidLatitude_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/nearest-stores")
                .param("latitude", "200.0")
                .param("longitude", "4.8952")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNearestStores_InvalidLongitude_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/nearest-stores")
                .param("latitude", "52.3702")
                .param("longitude", "200.0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNearestStores_NoStores_ReturnsEmptyList() throws Exception {
        // Clear all stores
        storeRepository.deleteAll();

        mockMvc.perform(get("/api/v1/nearest-stores")
                .param("latitude", "52.3702")
                .param("longitude", "4.8952")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}