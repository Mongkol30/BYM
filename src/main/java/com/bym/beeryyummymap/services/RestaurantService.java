package com.bym.beeryyummymap.services;

import com.bym.beeryyummymap.dto.MenuDto;
import com.bym.beeryyummymap.dto.RestaurantDetailDto;
import com.bym.beeryyummymap.dto.RestaurantRequestDto;
import com.bym.beeryyummymap.entity.locations;
import com.bym.beeryyummymap.entity.menus;
import com.bym.beeryyummymap.entity.restaurants;
import com.bym.beeryyummymap.repository.LocationRepository;
import com.bym.beeryyummymap.repository.MenusRepository;
import com.bym.beeryyummymap.repository.RestaurantsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {

    private final RestaurantsRepository restaurantsRepository;
    private final LocationRepository locationRepository;
    private final MenusRepository menusRepository;

    public RestaurantService(
            RestaurantsRepository restaurantsRepository,
            LocationRepository locationRepository,
            MenusRepository menusRepository
    ) {
        this.restaurantsRepository = restaurantsRepository;
        this.locationRepository = locationRepository;
        this.menusRepository = menusRepository;
    }

    @Transactional
    public RestaurantDetailDto createRestaurant(RestaurantRequestDto dto) {
        Timestamp now = Timestamp.from(Instant.now());

        // 1. Create Location
        locations loc = new locations();
        loc.setLocCode("LOC-" + System.currentTimeMillis());
        loc.setLatitude(dto.latitude());
        loc.setLongitude(dto.longitude());
        loc.setCreatedAt(now);
        loc.setUpdatedAt(now);
        locations savedLoc = locationRepository.save(loc);

        // 2. Create Restaurant
        restaurants res = new restaurants();
        res.setResCode("RES-" + System.currentTimeMillis());
        res.setName(dto.name());
        res.setDescription(dto.description());
        res.setImageUrl(dto.imageUrl());
        res.setStatus(dto.status() != null ? dto.status() : "ACTIVE");
        res.setLocationId(savedLoc.getId());
        res.setCreatedAt(now);
        res.setUpdatedAt(now);
        restaurants savedRes = restaurantsRepository.save(res);

        // 3. Create Menus if any
        List<MenuDto> createdMenus = new ArrayList<>();
        if (dto.menus() != null && !dto.menus().isEmpty()) {
            for (MenuDto mDto : dto.menus()) {
                menus m = new menus();
                m.setMenuCode("MNU-" + System.currentTimeMillis());
                m.setName(mDto.name());
                m.setPrice(mDto.price());
                m.setDescription(mDto.description());
                m.setImageUrl(mDto.imageUrl());
                m.setStatus(mDto.status() != null ? mDto.status() : "ACTIVE");
                m.setResId(savedRes.getId());
                m.setCreatedAt(now);
                m.setUpdatedAt(now);
                menus savedM = menusRepository.save(m);
                createdMenus.add(new MenuDto(
                        savedM.getId(),
                        savedM.getMenuCode(),
                        savedM.getName(),
                        savedM.getPrice(),
                        savedM.getDescription(),
                        savedM.getImageUrl(),
                        savedM.getStatus()
                ));
            }
        }

        return new RestaurantDetailDto(
                savedRes.getId(),
                savedRes.getResCode(),
                savedRes.getName(),
                savedRes.getDescription(),
                savedRes.getImageUrl(),
                savedRes.getStatus(),
                savedLoc.getId(),
                savedLoc.getLatitude(),
                savedLoc.getLongitude(),
                createdMenus
        );
    }

    @Transactional(readOnly = true)
    public RestaurantDetailDto getRestaurantById(UUID id) {
        restaurants res = restaurantsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + id));

        locations loc = null;
        if (res.getLocationId() != null) {
            loc = locationRepository.findById(res.getLocationId()).orElse(null);
        }

        List<menus> menuEntities = menusRepository.findByResId(res.getId());
        List<MenuDto> menuDtos = menuEntities.stream()
                .map(m -> new MenuDto(
                        m.getId(),
                        m.getMenuCode(),
                        m.getName(),
                        m.getPrice(),
                        m.getDescription(),
                        m.getImageUrl(),
                        m.getStatus()
                ))
                .toList();

        return new RestaurantDetailDto(
                res.getId(),
                res.getResCode(),
                res.getName(),
                res.getDescription(),
                res.getImageUrl(),
                res.getStatus(),
                res.getLocationId(),
                loc != null ? loc.getLatitude() : null,
                loc != null ? loc.getLongitude() : null,
                menuDtos
        );
    }

    @Transactional
    public RestaurantDetailDto updateRestaurant(UUID id, RestaurantRequestDto dto) {
        restaurants res = restaurantsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + id));

        Timestamp now = Timestamp.from(Instant.now());

        res.setName(dto.name());
        res.setDescription(dto.description());
        res.setImageUrl(dto.imageUrl());
        if (dto.status() != null) res.setStatus(dto.status());
        res.setUpdatedAt(now);
        restaurants savedRes = restaurantsRepository.save(res);

        locations loc = null;
        if (res.getLocationId() != null) {
            loc = locationRepository.findById(res.getLocationId()).orElse(null);
        }
        if (loc != null && dto.latitude() != null && dto.longitude() != null) {
            loc.setLatitude(dto.latitude());
            loc.setLongitude(dto.longitude());
            loc.setUpdatedAt(now);
            locationRepository.save(loc);
        }

        // Update menus: remove old and insert new ones
        menusRepository.deleteByResId(res.getId());
        List<MenuDto> updatedMenus = new ArrayList<>();
        if (dto.menus() != null && !dto.menus().isEmpty()) {
            for (MenuDto mDto : dto.menus()) {
                menus m = new menus();
                m.setMenuCode("MNU-" + System.currentTimeMillis());
                m.setName(mDto.name());
                m.setPrice(mDto.price());
                m.setDescription(mDto.description());
                m.setImageUrl(mDto.imageUrl());
                m.setStatus(mDto.status() != null ? mDto.status() : "ACTIVE");
                m.setResId(savedRes.getId());
                m.setCreatedAt(now);
                m.setUpdatedAt(now);
                menus savedM = menusRepository.save(m);
                updatedMenus.add(new MenuDto(
                        savedM.getId(),
                        savedM.getMenuCode(),
                        savedM.getName(),
                        savedM.getPrice(),
                        savedM.getDescription(),
                        savedM.getImageUrl(),
                        savedM.getStatus()
                ));
            }
        }

        return new RestaurantDetailDto(
                savedRes.getId(),
                savedRes.getResCode(),
                savedRes.getName(),
                savedRes.getDescription(),
                savedRes.getImageUrl(),
                savedRes.getStatus(),
                savedRes.getLocationId(),
                loc != null ? loc.getLatitude() : null,
                loc != null ? loc.getLongitude() : null,
                updatedMenus
        );
    }

    @Transactional
    public void deleteRestaurant(UUID id) {
        restaurants res = restaurantsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + id));

        // Soft delete: set status to INACTIVE
        res.setStatus("INACTIVE");
        res.setUpdatedAt(Timestamp.from(Instant.now()));
        restaurantsRepository.save(res);
    }
}
