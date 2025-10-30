package com.store.inventory.domain.repository;

import java.util.Optional;

import com.store.inventory.domain.model.Inventory;

public interface InventoryRepository {
    Optional<Inventory> findByProductCode(String productCode);
    Inventory save(Inventory inventory);
}