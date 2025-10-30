package com.store.products.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.store.products.domain.model.Product;

public interface ProductService {
    Product create(Product product);
    Product update(Long id, Product product);
    Product findById(Long id);
    Page<Product> findAll(Pageable pageable);
    void delete(Long id);
}