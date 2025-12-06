package com.bym.beeryyummymap.repository;

import com.bym.beeryyummymap.entity.locations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<locations, UUID> {
}
