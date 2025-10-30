package com.store.products.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.store.products.domain.model.Product;
import com.store.products.domain.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class GetAllProductsUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetAllProductsUseCase useCase;

    private Product p1;

    @BeforeEach
    void setUp() {
        p1 = new Product();
        p1.setCode("A");
        p1.setName("A name");
    }

    @Test
    void execute_ShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of(p1));
        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<Product> result = useCase.execute(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}
