package com.store.products.application.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

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

import com.store.products.application.usecase.CreateProductUseCase;
import com.store.products.application.usecase.DeleteProductUseCase;
import com.store.products.application.usecase.GetAllProductsUseCase;
import com.store.products.application.usecase.GetProductByCodeUseCase;
import com.store.products.application.usecase.UpdateProductUseCase;
import com.store.products.domain.model.Product;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private CreateProductUseCase createUseCase;
    @Mock
    private GetProductByCodeUseCase getByCodeUseCase;
    @Mock
    private GetAllProductsUseCase getAllUseCase;
    @Mock
    private UpdateProductUseCase updateUseCase;
    @Mock
    private DeleteProductUseCase deleteUseCase;

    @InjectMocks
    private ProductServiceImpl service;

    private Product p;

    @BeforeEach
    void setUp() {
        p = new Product();
        p.setCode("X");
        p.setName("Xname");
    }

    @Test
    void create_shouldDelegate() {
        when(createUseCase.execute(p)).thenReturn(p);
        assertEquals(p, service.create(p));
        verify(createUseCase).execute(p);
    }

    @Test
    void findByCode_shouldDelegate() {
        when(getByCodeUseCase.execute("C")).thenReturn(p);
        assertEquals(p, service.findByCode("C"));
        verify(getByCodeUseCase).execute("C");
    }

    @Test
    void findAll_shouldDelegate() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of(p));
        when(getAllUseCase.execute(pageable)).thenReturn(page);
        Page<Product> res = service.findAll(pageable);
        assertEquals(1, res.getTotalElements());
        verify(getAllUseCase).execute(pageable);
    }

    @Test
    void delete_shouldDelegate() {
        service.delete(10L);
        verify(deleteUseCase).execute(10L);
    }

    @Test
    void findById_shouldThrowUnsupported() {
        assertThrows(UnsupportedOperationException.class, () -> service.findById(1L));
    }
}
