package com.bym.beeryyummymap.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuDto(
        UUID id,
        String menuCode,
        String name,
        BigDecimal price,
        String description,
        String imageUrl,
        String status
) {}
