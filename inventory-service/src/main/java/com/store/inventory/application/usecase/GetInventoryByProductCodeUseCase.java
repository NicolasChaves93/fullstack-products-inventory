package com.store.inventory.application.usecase;

import org.springframework.stereotype.Service;

import com.store.inventory.domain.exception.InventoryNotFoundException;
import com.store.inventory.domain.model.Inventory;
import com.store.inventory.domain.out.ProductVerificationPort;
import com.store.inventory.domain.repository.InventoryRepository;

@Service
public class GetInventoryByProductCodeUseCase {

    private final InventoryRepository repo;
    private final ProductVerificationPort productVerifier;

    public GetInventoryByProductCodeUseCase(InventoryRepository repo, ProductVerificationPort productVerifier) {
        this.repo = repo;
        this.productVerifier = productVerifier;
    }

    public Inventory execute(String productCode) {
        // Verificar la existencia del producto consultando al products-service
        boolean productExists = productVerifier.isProductExist(productCode);

        if (!productExists) {
            // Si el producto NO existe en Products Service, lanzamos la excepción
            throw new InventoryNotFoundException("Producto no encontrado: " + productCode);
        }

        // Si el producto existe, buscamos el inventario en nuestra BD
        return repo.findByProductCode(productCode)
            // Si no hay registro en la BD, creamos un objeto Inventory con cantidad 0.
            .orElseGet(() -> new Inventory(productCode, 0));
    }
}
