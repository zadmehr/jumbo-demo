package com.jumbo.store.geo.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record NearestStoreRequest(
    @NotNull(message = "Latitude is required")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "Latitude must be a valid decimal number")
    String latitude,

    @NotNull(message = "Longitude is required")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "Longitude must be a valid decimal number")
    String longitude
) {}