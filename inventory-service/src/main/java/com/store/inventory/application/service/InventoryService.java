package com.store.inventory.application.service;

import com.store.inventory.domain.model.Inventory;

public interface InventoryService {
    
    // CU_I1: Consultar cantidad
    Inventory findByProductCode(String productCode);
    
    // CU_I2: Actualizar cantidad
    Inventory updateStock(String productCode, int quantityDelta);
}
