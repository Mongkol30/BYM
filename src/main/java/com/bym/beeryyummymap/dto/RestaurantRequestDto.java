package com.bym.beeryyummymap.dto;

import java.util.List;

public record RestaurantRequestDto(
        String name,
        String description,
        String imageUrl,
        String status,
        Double latitude,
        Double longitude,
        List<MenuDto> menus
) {}
