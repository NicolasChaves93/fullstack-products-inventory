package com.store.inventory.application.usecase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.store.inventory.domain.exception.InventoryNotFoundException;
import com.store.inventory.domain.model.Inventory;
import com.store.inventory.domain.out.ProductVerificationPort;
import com.store.inventory.domain.repository.InventoryRepository;

@ExtendWith(MockitoExtension.class)
class GetInventoryByProductCodeUseCaseTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductVerificationPort productVerifier;

    @InjectMocks
    private GetInventoryByProductCodeUseCase useCase;

    private Inventory testInventory;
    private final String PRODUCT_CODE = "TEST001";

    @BeforeEach
    void setUp() {
        testInventory = new Inventory();
        testInventory.setProductCode(PRODUCT_CODE);
        testInventory.setQuantity(10);
    }

    @Test
    void execute_ShouldReturnInventory_WhenProductExistsAndHasInventory() {
        when(productVerifier.isProductExist(PRODUCT_CODE)).thenReturn(true);
        when(inventoryRepository.findByProductCode(PRODUCT_CODE))
                .thenReturn(Optional.of(testInventory));

        Inventory result = useCase.execute(PRODUCT_CODE);

        assertNotNull(result);
        assertEquals(PRODUCT_CODE, result.getProductCode());
        assertEquals(10, result.getQuantity());
        verify(productVerifier).isProductExist(PRODUCT_CODE);
        verify(inventoryRepository).findByProductCode(PRODUCT_CODE);
    }

    @Test
    void execute_ShouldReturnNewInventory_WhenProductExistsButNoInventory() {
        when(productVerifier.isProductExist(PRODUCT_CODE)).thenReturn(true);
        when(inventoryRepository.findByProductCode(PRODUCT_CODE))
                .thenReturn(Optional.empty());

        Inventory result = useCase.execute(PRODUCT_CODE);

        assertNotNull(result);
        assertEquals(PRODUCT_CODE, result.getProductCode());
        assertEquals(0, result.getQuantity());
        verify(productVerifier).isProductExist(PRODUCT_CODE);
        verify(inventoryRepository).findByProductCode(PRODUCT_CODE);
    }

    @Test
    void execute_ShouldThrowException_WhenProductDoesNotExist() {
        when(productVerifier.isProductExist(PRODUCT_CODE)).thenReturn(false);

        assertThrows(InventoryNotFoundException.class, () -> {
            useCase.execute(PRODUCT_CODE);
        });

        verify(productVerifier).isProductExist(PRODUCT_CODE);
        verifyNoInteractions(inventoryRepository);
    }
}