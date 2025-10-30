package com.store.inventory.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.store.inventory.domain.model.Inventory;
import com.store.inventory.domain.repository.InventoryRepository;
import com.store.inventory.infrastructure.entity.InventoryEntity;
import com.store.inventory.infrastructure.mapper.InventoryMapper;

@Repository
public class JpaInventoryRepository implements InventoryRepository {

    private final SpringDataInventoryRepository springRepo;
    private final InventoryMapper mapper;

    public JpaInventoryRepository(SpringDataInventoryRepository springRepo, InventoryMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public Optional<Inventory> findByProductCode(String productCode) {
        return springRepo.findByProductCode(productCode).map(mapper::toDomain);
    }

    @Override
    public Inventory save(Inventory inventory) {
        inventory.setLastUpdated(LocalDateTime.now());
        InventoryEntity saved = springRepo.save(mapper.toEntity(inventory));
        return mapper.toDomain(saved);
    }
}
