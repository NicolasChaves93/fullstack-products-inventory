package com.store.products.application.service.impl;

import com.store.products.application.service.ProductService;
import com.store.products.application.usecase.*;
import com.store.products.domain.model.Product;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProductServiceImpl implements ProductService {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    public ProductServiceImpl(
            CreateProductUseCase createProductUseCase,
            GetProductByIdUseCase getProductByIdUseCase,
            GetAllProductsUseCase getAllProductsUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeleteProductUseCase deleteProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.getAllProductsUseCase = getAllProductsUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @Override
    public Product create(Product product) {
        return createProductUseCase.execute(product);
    }

    @Override
    public Product update(Long id, Product product) {
        return updateProductUseCase.execute(id, product);
    }

    @Override
    public Product findById(Long id) {
        return getProductByIdUseCase.execute(id);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return getAllProductsUseCase.execute(pageable);
    }

    @Override
    public void delete(Long id) {
        deleteProductUseCase.execute(id);
    }
}
