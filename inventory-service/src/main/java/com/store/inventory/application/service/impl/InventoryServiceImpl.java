package com.store.inventory.application.service.impl;

import org.springframework.stereotype.Service;

import com.store.inventory.application.service.InventoryService;
import com.store.inventory.application.usecase.GetInventoryByProductCodeUseCase;
import com.store.inventory.application.usecase.UpdateStockUseCase;
import com.store.inventory.domain.model.Inventory;

@Service
public class InventoryServiceImpl implements InventoryService { // 👈 Implementa la interfaz fachada

    // Inyectar los Casos de Uso (que contienen la lógica de negocio)
    private final GetInventoryByProductCodeUseCase getInventoryByProductCodeUseCase;
    private final UpdateStockUseCase updateStockUseCase;

    public InventoryServiceImpl(
            GetInventoryByProductCodeUseCase getInventoryByProductCodeUseCase,
            UpdateStockUseCase updateStockUseCase) {
        this.getInventoryByProductCodeUseCase = getInventoryByProductCodeUseCase;
        this.updateStockUseCase = updateStockUseCase;
    }

    @Override
    public Inventory findByProductCode(String productCode) {
        // Delegación de CU_I1
        return getInventoryByProductCodeUseCase.execute(productCode);
    }

    @Override
    public Inventory updateStock(String productCode, int quantityDelta) {
        // Delegación de CU_I2
        // Este método ya incluye la validación de Products Service y la emisión del evento.
        return updateStockUseCase.execute(productCode, quantityDelta);
    }
}
