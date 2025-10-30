package com.store.products.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.store.products.domain.model.Product;
import com.store.products.domain.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductUseCase useCase;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setCode("C-1");
        product.setName("Create Test");
    }

    @Test
    void execute_ShouldSaveAndReturnProduct() {
        when(productRepository.save(product)).thenReturn(product);

        Product result = useCase.execute(product);

        assertNotNull(result);
        assertEquals("C-1", result.getCode());
    }
}
