package com.store.inventory.infrastructure.adapter;

import org.springframework.stereotype.Component;

import com.store.inventory.domain.model.Inventory;
import com.store.inventory.domain.out.StockEventPublisherPort;

@Component
public class ConsoleEventPublisherAdapter implements StockEventPublisherPort {

    @Override
    public void publish(Inventory inventory, String reason) {
        // Implementación de Infraestructura para el CU_I3 (Log)
        System.out.printf("[EVENTO STOCK] Product: %s, Nueva cantidad: %d, Razon: %s%n", 
            inventory.getProductCode(), inventory.getQuantity(), reason);
    }
}