package com.jumbo.store.geo.controller;

import com.jumbo.store.geo.controller.dto.StoreDTO;
import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StoreService storeService;

    @InjectMocks
    private StoreController storeController;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations for mocks
        MockitoAnnotations.openMocks(this);
    }

}