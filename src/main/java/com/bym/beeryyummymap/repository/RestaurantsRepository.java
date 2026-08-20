package com.bym.beeryyummymap.repository;

import com.bym.beeryyummymap.entity.Location;
import com.bym.beeryyummymap.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantsRepository extends JpaRepository<Restaurant, UUID> {
}
