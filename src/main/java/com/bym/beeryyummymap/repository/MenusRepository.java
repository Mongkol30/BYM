package com.bym.beeryyummymap.repository;

import com.bym.beeryyummymap.entity.locations;
import com.bym.beeryyummymap.entity.menus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenusRepository extends JpaRepository<menus, UUID> {
}
