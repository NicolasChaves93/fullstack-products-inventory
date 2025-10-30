package com.store.inventory.domain.out;

import com.store.inventory.domain.model.Inventory;

/**
 * Port de Salida para notificar cambios en el inventario.
 */
public interface StockEventPublisherPort {
    void publish(Inventory inventory, String reason);
}
