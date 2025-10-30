package com.store.products.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.store.products.domain.model.Product;
import com.store.products.infrastructure.entity.ProductEntity;

@Component
public class ProductMapper {

    public Product toDomain(ProductEntity entity) {
        if (entity == null) return null;

        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setCode(entity.getCode());
        product.setDescription(entity.getDescription());
        product.setPrice(entity.getPrice());
        product.setActive(entity.isActive());
        product.setCreatedAt(entity.getCreatedAt());
        product.setUpdatedAt(entity.getUpdatedAt());
        return product;
    }

    public ProductEntity toEntity(Product domain) {
        if (domain == null) return null;

        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setActive(domain.getActive() != null ? domain.getActive() : false);
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
