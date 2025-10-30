package com.store.inventory.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class InventoryTest {

    @Test
    void constructor_ShouldCreateInventory_WithEmptyConstructor() {
        Inventory inventory = new Inventory();

        assertNull(inventory.getId());
        assertNull(inventory.getProductCode());
        assertNull(inventory.getQuantity());
        assertNull(inventory.getLastUpdated());
        assertNull(inventory.getVersion());
    }

    @Test
    void constructor_ShouldCreateInventory_WithProductCodeAndQuantity() {
        String productCode = "TEST001";
        Integer quantity = 10;

        Inventory inventory = new Inventory(productCode, quantity);

        assertEquals(productCode, inventory.getProductCode());
        assertEquals(quantity, inventory.getQuantity());
        assertNull(inventory.getId());
        assertNull(inventory.getLastUpdated());
        assertNull(inventory.getVersion());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        Inventory inventory = new Inventory();
        Long id = 1L;
        String productCode = "TEST001";
        Integer quantity = 10;
        LocalDateTime lastUpdated = LocalDateTime.now();
        Integer version = 1;

        inventory.setId(id);
        inventory.setProductCode(productCode);
        inventory.setQuantity(quantity);
        inventory.setLastUpdated(lastUpdated);
        inventory.setVersion(version);

        assertEquals(id, inventory.getId());
        assertEquals(productCode, inventory.getProductCode());
        assertEquals(quantity, inventory.getQuantity());
        assertEquals(lastUpdated, inventory.getLastUpdated());
        assertEquals(version, inventory.getVersion());
    }
}