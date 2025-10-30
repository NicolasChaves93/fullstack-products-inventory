package com.store.products.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
class GetProductByCodeUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductByCodeUseCase useCase;

    private final String CODE = "P-001";
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setCode(CODE);
        product.setName("Test Product");
    }

    @Test
    void execute_ShouldReturnProduct_WhenFound() {
        when(productRepository.findByCode(CODE)).thenReturn(Optional.of(product));

        Product result = useCase.execute(CODE);

        assertNotNull(result);
        assertEquals(CODE, result.getCode());
    }

    @Test
    void execute_ShouldThrow_WhenNotFound() {
        when(productRepository.findByCode(CODE)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> useCase.execute(CODE));
    }
}
