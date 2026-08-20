package com.bym.beeryyummymap.repository;

import com.bym.beeryyummymap.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MenusRepository extends JpaRepository<Menu, UUID> {
    List<Menu> findByResId(UUID resId);
    void deleteByResId(UUID resId);
}
