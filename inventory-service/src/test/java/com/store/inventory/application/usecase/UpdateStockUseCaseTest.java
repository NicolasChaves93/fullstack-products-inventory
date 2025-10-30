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

import com.store.inventory.domain.exception.ProductNotFoundException;
import com.store.inventory.domain.model.Inventory;
import com.store.inventory.domain.out.ProductVerificationPort;
import com.store.inventory.domain.out.StockEventPublisherPort;
import com.store.inventory.domain.repository.InventoryRepository;

@ExtendWith(MockitoExtension.class)
class UpdateStockUseCaseTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductVerificationPort productVerifier;

    @Mock
    private StockEventPublisherPort eventPublisher;

    @InjectMocks
    private UpdateStockUseCase useCase;

    private Inventory testInventory;
    private final String PRODUCT_CODE = "TEST001";

    @BeforeEach
    void setUp() {
        testInventory = new Inventory();
        testInventory.setProductCode(PRODUCT_CODE);
        testInventory.setQuantity(10);
    }

    @Test
    void execute_ShouldUpdateStock_WhenProductExistsAndQuantityIsValid() {
        when(productVerifier.isProductExist(PRODUCT_CODE)).thenReturn(true);
        when(inventoryRepository.findByProductCode(PRODUCT_CODE))
                .thenReturn(Optional.of(testInventory));
        when(inventoryRepository.save(any(Inventory.class)))
                .thenReturn(testInventory);

        Inventory result = useCase.execute(PRODUCT_CODE, 5);

        assertNotNull(result);
        assertEquals(PRODUCT_CODE, result.getProductCode());
        verify(productVerifier).isProductExist(PRODUCT_CODE);
        verify(inventoryRepository).findByProductCode(PRODUCT_CODE);
        verify(inventoryRepository).save(any(Inventory.class));
        verify(eventPublisher).publish(any(Inventory.class), any(String.class));
    }

    @Test
    void execute_ShouldCreateNewInventory_WhenProductExistsButNoInventory() {
        when(productVerifier.isProductExist(PRODUCT_CODE)).thenReturn(true);
        when(inventoryRepository.findByProductCode(PRODUCT_CODE))
                .thenReturn(Optional.empty());
        when(inventoryRepository.save(any(Inventory.class)))
                .thenReturn(testInventory);

        Inventory result = useCase.execute(PRODUCT_CODE, 5);

        assertNotNull(result);
        assertEquals(PRODUCT_CODE, result.getProductCode());
        verify(productVerifier).isProductExist(PRODUCT_CODE);
        verify(inventoryRepository).findByProductCode(PRODUCT_CODE);
        verify(inventoryRepository, times(2)).save(any(Inventory.class));
        verify(eventPublisher).publish(any(Inventory.class), any(String.class));
    }

    @Test
    void execute_ShouldThrowException_WhenProductDoesNotExist() {
        when(productVerifier.isProductExist(PRODUCT_CODE)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> {
            useCase.execute(PRODUCT_CODE, 5);
        });

        verify(productVerifier).isProductExist(PRODUCT_CODE);
        verifyNoInteractions(inventoryRepository, eventPublisher);
    }

    @Test
    void execute_ShouldThrowException_WhenQuantityIsNegative() {
        when(productVerifier.isProductExist(PRODUCT_CODE)).thenReturn(true);
        when(inventoryRepository.findByProductCode(PRODUCT_CODE)).thenReturn(Optional.of(testInventory));

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(PRODUCT_CODE, -5);
        });

        verify(productVerifier).isProductExist(PRODUCT_CODE);
        verify(inventoryRepository).findByProductCode(PRODUCT_CODE);
        verifyNoMoreInteractions(inventoryRepository, eventPublisher);
    }
}