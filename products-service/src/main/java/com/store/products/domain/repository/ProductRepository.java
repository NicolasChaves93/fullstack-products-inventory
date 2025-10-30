package com.store.products.domain.repository;


import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.store.products.domain.model.Product;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    Optional<Product> findByCode(String code);
    Page<Product> findAll(Pageable pageable);
    void delete(Long id);
}