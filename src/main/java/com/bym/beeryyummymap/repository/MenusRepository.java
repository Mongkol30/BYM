package com.bym.beeryyummymap.repository;

import com.bym.beeryyummymap.entity.menus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MenusRepository extends JpaRepository<menus, UUID> {
    List<menus> findByResId(UUID resId);
    void deleteByResId(UUID resId);
}
