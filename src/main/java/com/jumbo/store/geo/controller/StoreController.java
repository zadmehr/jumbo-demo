package com.jumbo.store.geo.controller;

import com.jumbo.store.geo.controller.dto.NearestStoreRequest;
import com.jumbo.store.geo.controller.dto.StoreDTO;
import com.jumbo.store.geo.service.StoreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
@Tag(name = "Store API", description = "API for managing stores and their locations")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/nearest-stores")
    @Operation(summary = "Get nearest stores", description = "Returns the 5 closest stores to the given latitude and longitude")
    public List<StoreDTO> getNearestStores(@Valid NearestStoreRequest request) {

        return storeService.getNearestStores(request.latitude(), request.longitude()).stream()
                .map(StoreDTO::fromStore)
                .toList();

    }
}