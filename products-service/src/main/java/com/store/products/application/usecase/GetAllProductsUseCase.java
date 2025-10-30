package com.store.products.application.usecase;

import com.store.products.domain.model.Product;
import com.store.products.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class GetAllProductsUseCase {

    private final ProductRepository productRepository;

    public GetAllProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> execute(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}