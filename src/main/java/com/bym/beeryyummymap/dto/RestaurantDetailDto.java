package com.bym.beeryyummymap.dto;

import java.util.List;
import java.util.UUID;

public record RestaurantDetailDto(
        UUID id,
        String resCode,
        String name,
        String description,
        String imageUrl,
        String status,
        UUID locationId,
        Double latitude,
        Double longitude,
        List<MenuDto> menus
) {}
