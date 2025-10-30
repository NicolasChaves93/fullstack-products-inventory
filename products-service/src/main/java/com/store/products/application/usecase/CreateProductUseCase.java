package com.store.products.application.usecase;

import org.springframework.stereotype.Service;

import com.store.products.domain.model.Product;
import com.store.products.domain.repository.ProductRepository;

@Service
public class CreateProductUseCase {

    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Product product) {
        return productRepository.save(product);
    }
}
