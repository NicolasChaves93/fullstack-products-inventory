package com.store.inventory.application.usecase;

import org.springframework.stereotype.Service;

import com.store.inventory.domain.exception.ProductNotFoundException;
import com.store.inventory.domain.model.Inventory;
import com.store.inventory.domain.out.ProductVerificationPort;
import com.store.inventory.domain.out.StockEventPublisherPort;
import com.store.inventory.domain.repository.InventoryRepository;

import jakarta.transaction.Transactional;

@Service
public class UpdateStockUseCase {

	private final InventoryRepository inventoryRepo;
	private final ProductVerificationPort productVerificationPort;
	private final StockEventPublisherPort eventPublisherPort;

	public UpdateStockUseCase(
	        InventoryRepository inventoryRepo,
	        ProductVerificationPort productVerificationPort,
	        StockEventPublisherPort eventPublisherPort) {
	        this.inventoryRepo = inventoryRepo;
	        this.productVerificationPort = productVerificationPort;
	        this.eventPublisherPort = eventPublisherPort;
	    }

	@Transactional
    public Inventory execute(String productCode, int quantityDelta) {
        // PASO 1: Validación de Existencia de Producto (Llamada al Microservicio)
        // El Port inyectado invoca al Adaptador (ProductServiceAdapter) que hace la llamada HTTP.
        if (!productVerificationPort.isProductExist(productCode)) {
            
        	throw new ProductNotFoundException(productCode);
        }
        
        // PASO 2: Lógica de Inventario
        Inventory inventory = inventoryRepo.findByProductCode(productCode)
                .orElseGet(() -> {
                    // Si el producto existe en PS pero no tiene inventario, crea uno nuevo en cero.
                    Inventory i = new Inventory();
                    i.setProductCode(productCode);
                    i.setQuantity(0);
                    return inventoryRepo.save(i);
                });

        int newQty = quantityDelta;
        if (newQty < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        inventory.setQuantity(newQty);
        Inventory updated = inventoryRepo.save(inventory);

        // PASO 3: Emitir Evento de Stock (CU_I3)
        eventPublisherPort.publish(updated, "Inevntario actualizado con cantidad: " + quantityDelta);

        return updated;
    }
}
