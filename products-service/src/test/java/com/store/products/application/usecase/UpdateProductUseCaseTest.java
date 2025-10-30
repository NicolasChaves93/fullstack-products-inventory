package com.store.products.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
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
class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductUseCase useCase;

    private Product existing;

    @BeforeEach
    void setUp() {
        existing = new Product();
        existing.setId(1L);
        existing.setName("Old");
        existing.setPrice(BigDecimal.valueOf(10));
    }

    @Test
    void execute_ShouldUpdateFields_WhenPartialProvided() {
        Product partial = new Product();
        partial.setName("New");
        partial.setPrice(BigDecimal.valueOf(15));

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);

        Product result = useCase.execute(1L, partial);

        assertNotNull(result);
        assertEquals("New", result.getName());
        assertEquals(0, BigDecimal.valueOf(15).compareTo(result.getPrice()));
        verify(productRepository).save(existing);
    }

    @Test
    void execute_ShouldThrow_WhenNotFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> useCase.execute(2L, new Product()));
    }
}
