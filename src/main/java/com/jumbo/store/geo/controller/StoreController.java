package com.jumbo.store.geo.controller;

import com.jumbo.store.geo.controller.dto.StoreDTO;
import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.service.StoreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/v1") // Base path for all endpoints in this controller
@Validated
@Tag(name = "Store API", description = "API for managing stores and their locations")
public class StoreController {

    private final StoreService storeService;

    @Autowired
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

    @Operation(summary = "Get nearest stores", description = "Returns the 5 closest stores to the given latitude and longitude", responses = {
            @ApiResponse(responseCode = "200", description = "List of nearest stores"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    public List<StoreDTO> getNearestStores(
            // Default Latitude 52.37867
            @Parameter(description = "Latitude of the location", required = true, example = "52.37867") 
            @RequestParam @NotNull(message = "Latitude is required") @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "Latitude must be a valid decimal number") String latitude,
            // Default Longitude 4.8838
            @Parameter(description = "Longitude of the location", required = true, example = "4.8838")
            @RequestParam @NotNull(message = "Longitude is required") @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "Longitude must be a valid decimal number") String longitude) {
        double lat = Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);
        return storeService.getNearestStores(lat, lon).stream()
                .map(StoreDTO::fromStore)
                .toList();
    }

    @GetMapping("/secure-api")
    public String secureApi() {
        return "This is a secure API endpoint";
    }
}