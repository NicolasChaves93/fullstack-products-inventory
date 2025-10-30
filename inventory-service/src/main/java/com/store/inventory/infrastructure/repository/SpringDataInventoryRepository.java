package com.store.inventory.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.inventory.infrastructure.entity.InventoryEntity;

public interface SpringDataInventoryRepository extends JpaRepository<InventoryEntity, Long> {
    Optional<InventoryEntity> findByProductCode(String productCode);
}
