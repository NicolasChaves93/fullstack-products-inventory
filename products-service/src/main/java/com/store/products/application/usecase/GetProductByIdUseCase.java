package com.store.products.application.usecase;

import com.store.products.domain.model.Product;
import com.store.products.domain.repository.ProductRepository;
import com.store.products.domain.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetProductByIdUseCase {

    private final ProductRepository productRepository;

    public GetProductByIdUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }
}
