package com.bym.beeryyummymap.controller;

import com.bym.beeryyummymap.dto.RestaurantDetailDto;
import com.bym.beeryyummymap.dto.RestaurantRequestDto;
import com.bym.beeryyummymap.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<RestaurantDetailDto> createRestaurant(@RequestBody RestaurantRequestDto dto) {
        RestaurantDetailDto result = restaurantService.createRestaurant(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDetailDto> getRestaurantById(@PathVariable UUID id) {
        RestaurantDetailDto result = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDetailDto> updateRestaurant(
            @PathVariable UUID id,
            @RequestBody RestaurantRequestDto dto
    ) {
        RestaurantDetailDto result = restaurantService.updateRestaurant(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable UUID id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
