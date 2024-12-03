package com.jumbo.store.geo.controller;

import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.service.StoreService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/v1") // Base path for all endpoints in this controller
@Validated
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    /**
     * Endpoint to get the 5 closest Jumbo stores to a given position.
     * 
     * @param latitude  the latitude of the position
     * @param longitude the longitude of the position
     * @return a list of the 5 closest stores
     */
    @GetMapping("/nearest-stores")
    public List<Store> getNearestStores(
            @RequestParam @NotNull(message = "Latitude is required") 
            @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "Latitude must be a valid decimal number") String latitude,
            
            @RequestParam @NotNull(message = "Longitude is required") 
            @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "Longitude must be a valid decimal number") String longitude) {
        double lat = Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);
        return storeService.getNearestStores(lat, lon);
    }
}