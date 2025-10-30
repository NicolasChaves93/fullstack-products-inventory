package com.store.inventory.application.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.store.inventory.application.usecase.GetInventoryByProductCodeUseCase;
import com.store.inventory.application.usecase.UpdateStockUseCase;
import com.store.inventory.domain.model.Inventory;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private GetInventoryByProductCodeUseCase getInventoryByProductCodeUseCase;

    @Mock
    private UpdateStockUseCase updateStockUseCase;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory testInventory;

    @BeforeEach
    void setUp() {
        testInventory = new Inventory();
        testInventory.setProductCode("TEST001");
        testInventory.setQuantity(10);
    }

    @Test
    void findByProductCode_ShouldReturnInventory_WhenProductExists() {
        when(getInventoryByProductCodeUseCase.execute("TEST001"))
                .thenReturn(testInventory);

        Inventory result = inventoryService.findByProductCode("TEST001");

        assertNotNull(result);
        assertEquals("TEST001", result.getProductCode());
        assertEquals(10, result.getQuantity());
        verify(getInventoryByProductCodeUseCase).execute("TEST001");
    }

    @Test
    void updateStock_ShouldUpdateAndReturnInventory_WhenRequestIsValid() {
        when(updateStockUseCase.execute("TEST001", 5))
                .thenReturn(testInventory);

        Inventory result = inventoryService.updateStock("TEST001", 5);

        assertNotNull(result);
        assertEquals("TEST001", result.getProductCode());
        assertEquals(10, result.getQuantity());
        verify(updateStockUseCase).execute("TEST001", 5);
    }
}