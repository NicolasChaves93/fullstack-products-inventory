package com.store.products.infrastructure.repository;

import com.store.products.domain.model.Product;
import com.store.products.domain.repository.ProductRepository;
import com.store.products.infrastructure.entity.ProductEntity;
import com.store.products.infrastructure.mapper.ProductMapper;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public class JpaProductRepository implements ProductRepository {

    private final SpringDataProductRepository springDataRepository;
    private final ProductMapper mapper;

    public JpaProductRepository(SpringDataProductRepository springDataRepository, ProductMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        ProductEntity saved = springDataRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Product> findByCode(String code) {
        return springDataRepository.findByCode(code)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return springDataRepository.findAll(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public void delete(Long id) {
        springDataRepository.deleteById(id);
    }
}
