package com.store.inventory.interfaces.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.inventory.application.service.InventoryService;
import com.store.inventory.domain.model.Inventory;
import com.store.inventory.interfaces.dto.InventoryRequest;
import com.store.inventory.interfaces.dto.InventoryResponse;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryController {

    // 1. Inyectar la Interfaz Fachada (Port de Entrada)
    private final InventoryService inventoryService; 

    public InventoryController(InventoryService inventoryService) { // 👈 Constructor más simple
        this.inventoryService = inventoryService;
    }

    // CU_I1: Consultar cantidad
    @GetMapping("/{productCode}")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable String productCode) {
        Inventory inventory = inventoryService.findByProductCode(productCode); 
        
        InventoryResponse response = new InventoryResponse();
        response.setProductCode(inventory.getProductCode());
        response.setQuantity(inventory.getQuantity());
        response.setLastUpdated(inventory.getLastUpdated());
        return ResponseEntity.ok(response);
    }

    // CU_I2: Actualizar cantidad
    @PatchMapping("/update")
    public ResponseEntity<InventoryResponse> updateStock(@RequestBody InventoryRequest request) {
        Inventory updated = inventoryService.updateStock(request.getProductCode(), request.getQuantity()); 
        
        InventoryResponse response = new InventoryResponse();
        response.setProductCode(updated.getProductCode());
        response.setQuantity(updated.getQuantity());
        response.setLastUpdated(updated.getLastUpdated());
        return ResponseEntity.ok(response);
    }
}