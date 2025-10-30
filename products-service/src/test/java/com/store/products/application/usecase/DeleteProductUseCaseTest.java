package com.store.products.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.store.products.domain.exception.ProductNotFoundException;
import com.store.products.domain.model.Product;
import com.store.products.domain.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class DeleteProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductUseCase useCase;

    private Product existing;

    @BeforeEach
    void setUp() {
        existing = new Product();
        existing.setId(5L);
    }

    @Test
    void execute_ShouldDelete_WhenExists() {
        when(productRepository.findById(5L)).thenReturn(Optional.of(existing));
        doNothing().when(productRepository).delete(5L);

        useCase.execute(5L);

        verify(productRepository).delete(5L);
    }

    @Test
    void execute_ShouldThrow_WhenNotFound() {
        when(productRepository.findById(6L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> useCase.execute(6L));
    }
}
