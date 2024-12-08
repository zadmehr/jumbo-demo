// package com.jumbo.store.geo.controller;

// import com.jumbo.store.geo.controller.dto.NearestStoreRequest;
// import com.jumbo.store.geo.controller.dto.StoreDTO;
// import com.jumbo.store.geo.service.StoreService;
// import com.jumbo.store.geo.model.Store;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// import java.util.Arrays;
// import java.util.List;

// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest(StoreController.class)
// class StoreControllerTest {

//     @Mock
//     private StoreService storeService;

//     @Autowired
//     private MockMvc mockMvc;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void testGetNearestStores_validRequest() throws Exception {
//         // Arrange
//         String latitude = "52.3784";
//         String longitude = "4.9009";
//         NearestStoreRequest request = new NearestStoreRequest(latitude, longitude);
//         Store mockStore = new Store(); // Add properties as needed
//         List<Store> mockStores = Arrays.asList(mockStore, mockStore); // Mock stores
//         when(storeService.getNearestStores(latitude, longitude)).thenReturn(mockStores);

//         // Act & Assert
//         mockMvc.perform(MockMvcRequestBuilders
//                 .get("/api/v1/nearest-stores")
//                 .param("latitude", latitude)
//                 .param("longitude", longitude)
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.length()").value(2)) // Expecting 2 stores as mocked
//                 .andExpect(jsonPath("$[0].uuid").exists()); // Example of checking a property, adjust as needed

//         verify(storeService, times(1)).getNearestStores(latitude, longitude);
//     }

//     @Test
//     void testGetNearestStores_invalidLatitude() throws Exception {
//         // Arrange
//         String invalidLatitude = "100.0";  // Invalid latitude
//         String longitude = "4.9009";
        
//         // Act & Assert
//         mockMvc.perform(MockMvcRequestBuilders
//                 .get("/api/v1/nearest-stores")
//                 .param("latitude", invalidLatitude)
//                 .param("longitude", longitude)
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isBadRequest())
//                 .andExpect(jsonPath("$.message").value("Invalid latitude value: 100.0"));
        
//         verify(storeService, times(0)).getNearestStores(anyString(), anyString());
//     }

//     @Test
//     void testGetNearestStores_invalidLongitude() throws Exception {
//         // Arrange
//         String latitude = "52.3784";
//         String invalidLongitude = "200.0";  // Invalid longitude
        
//         // Act & Assert
//         mockMvc.perform(MockMvcRequestBuilders
//                 .get("/api/v1/nearest-stores")
//                 .param("latitude", latitude)
//                 .param("longitude", invalidLongitude)
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isBadRequest())
//                 .andExpect(jsonPath("$.message").value("Invalid longitude value: 200.0"));

//         verify(storeService, times(0)).getNearestStores(anyString(), anyString());
//     }

//     @Test
//     void testGetNearestStores_emptyResult() throws Exception {
//         // Arrange
//         String latitude = "52.3784";
//         String longitude = "4.9009";
//         List<Store> emptyStoreList = Arrays.asList(); // No stores
//         when(storeService.getNearestStores(latitude, longitude)).thenReturn(emptyStoreList);

//         // Act & Assert
//         mockMvc.perform(MockMvcRequestBuilders
//                 .get("/api/v1/nearest-stores")
//                 .param("latitude", latitude)
//                 .param("longitude", longitude)
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.length()").value(0)); // Expecting empty list

//         verify(storeService, times(1)).getNearestStores(latitude, longitude);
//     }
// }
