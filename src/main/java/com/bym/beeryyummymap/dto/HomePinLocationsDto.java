package com.bym.beeryyummymap.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record HomePinLocationsDto (
        UUID locationId,
        UUID restaurantId,
        String restaurantName,
        String imageUrl,
        BigDecimal latitude,
        BigDecimal longitude,
        String matchedMenuName
) {}
