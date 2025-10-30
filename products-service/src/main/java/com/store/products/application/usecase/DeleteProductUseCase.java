package com.store.products.application.usecase;

import com.store.products.domain.exception.ProductNotFoundException;
import com.store.products.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductUseCase {

    private final ProductRepository productRepository;

    public DeleteProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        productRepository.delete(id);
    }
}