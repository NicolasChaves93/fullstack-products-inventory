package com.store.products.application.usecase;

import com.store.products.domain.model.Product;
import com.store.products.domain.repository.ProductRepository;
import com.store.products.domain.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetProductByCodeUseCase {

    private final ProductRepository productRepository;

    public GetProductByCodeUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(String code) {
        return productRepository.findByCode(code)
                .orElseThrow(() -> new ProductNotFoundException("Prodcuto con codigo " + code + " no encontrado"));
    }
}
