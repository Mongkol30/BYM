package com.bym.beeryyummymap.repository;

import com.bym.beeryyummymap.entity.locations;
import com.bym.beeryyummymap.entity.restaurants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantsRepository extends JpaRepository<restaurants, UUID> {
}
