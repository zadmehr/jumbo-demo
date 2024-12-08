package com.jumbo.store.geo.controller;

import com.jumbo.store.geo.config.TestContainersMongoConfig;
import com.jumbo.store.geo.controller.dto.StoreDTO;
import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.repository.StoreRepository;

import io.micrometer.core.ipc.http.HttpSender.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestContainersMongoConfig.Initializer.class)
@ActiveProfiles("test") // Uses the "test" profile for the test configuration
class StoreControllerTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @BeforeEach
    void setUp() {
        // Clear the database before each test
        storeRepository.deleteAll();
        // Insert test data
        initialization();
    }

    @Test
    void getNearestStores() {
        ResponseEntity<List<StoreDTO>> response = testRestTemplate.exchange(
                "/api/v1/nearest-stores?latitude=52.37867&longitude=4.883832",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StoreDTO>>() {});
        // Assert
        assert response.getStatusCode().is2xxSuccessful();
        // List<StoreDTO> stores = response.getBody();
        // assert stores != null;
        // assert stores.size() == 2;
        // assert stores.get(0).getCity().equals("Amsterdam");
        // assert stores.get(1).getCity().equals("Rotterdam");
    }

    private void initialization() {
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
}