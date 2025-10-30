package com.store.inventory.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.store.inventory.domain.model.Inventory;
import com.store.inventory.infrastructure.entity.InventoryEntity;

@Component
public class InventoryMapper {

    public Inventory toDomain(InventoryEntity e) {
        if (e == null) return null;
        Inventory d = new Inventory();
        d.setId(e.getId());
        d.setProductCode(e.getProductCode());
        d.setQuantity(e.getQuantity());
        d.setLastUpdated(e.getLastUpdated());
        d.setVersion(e.getVersion());
        return d;
    }

    public InventoryEntity toEntity(Inventory d) {
        if (d == null) return null;
        InventoryEntity e = new InventoryEntity();
        e.setId(d.getId());
        e.setProductCode(d.getProductCode());
        e.setQuantity(d.getQuantity());
        e.setLastUpdated(d.getLastUpdated());
        e.setVersion(d.getVersion());
        return e;
    }
}
