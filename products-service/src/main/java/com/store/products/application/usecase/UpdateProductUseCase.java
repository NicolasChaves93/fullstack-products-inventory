package com.store.products.application.usecase;

import com.store.products.domain.model.Product;
import com.store.products.domain.repository.ProductRepository;
import com.store.products.domain.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductUseCase {

    private final ProductRepository productRepository;

    public UpdateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Long id, Product partialUpdate) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        if (partialUpdate.getName() != null) {
            existing.setName(partialUpdate.getName());
        }
        if (partialUpdate.getDescription() != null) {
            existing.setDescription(partialUpdate.getDescription());
        }
        if (partialUpdate.getPrice() != null) {
            existing.setPrice(partialUpdate.getPrice());
        }
        if (partialUpdate.getActive() != null) {
            existing.setActive(partialUpdate.getActive());
        }

        return productRepository.save(existing);
    }
}