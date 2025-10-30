package com.store.products.infrastructure.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.store.products.domain.model.Product;
import com.store.products.infrastructure.entity.ProductEntity;

class ProductMapperTest {

    private ProductMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductMapper();
    }

    @Test
    void toDomain_ShouldMapAllFields() {
        ProductEntity e = new ProductEntity();
        e.setId(3L);
        e.setCode("CODE");
        e.setName("Name");
        e.setDescription("Desc");
        e.setPrice(BigDecimal.valueOf(12.5));
        e.setActive(true);
        LocalDateTime now = LocalDateTime.now();
        e.setCreatedAt(now);
        e.setUpdatedAt(now);

        Product p = mapper.toDomain(e);
        assertNotNull(p);
        assertEquals(3L, p.getId());
        assertEquals("CODE", p.getCode());
        assertEquals("Name", p.getName());
        assertEquals("Desc", p.getDescription());
        assertEquals(0, BigDecimal.valueOf(12.5).compareTo(p.getPrice()));
        assertTrue(p.getActive());
        assertEquals(now, p.getCreatedAt());
        assertEquals(now, p.getUpdatedAt());
    }

    @Test
    void toEntity_ShouldMapAndDefaultActiveFalseWhenNull() {
        Product d = new Product();
        d.setId(4L);
        d.setCode("C2");
        d.setName("N2");
        d.setDescription("D2");
        d.setPrice(BigDecimal.valueOf(5));
        d.setActive(null);
        LocalDateTime now = LocalDateTime.now();
        d.setCreatedAt(now);
        d.setUpdatedAt(now);

        ProductEntity e = mapper.toEntity(d);
        assertNotNull(e);
        assertEquals(4L, e.getId());
        assertEquals("C2", e.getCode());
        assertEquals("N2", e.getName());
        assertEquals("D2", e.getDescription());
        assertEquals(0, BigDecimal.valueOf(5).compareTo(e.getPrice()));
        assertFalse(e.isActive());
        assertEquals(now, e.getCreatedAt());
        assertEquals(now, e.getUpdatedAt());
    }

    @Test
    void toDomain_NullReturnsNull() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void toEntity_NullReturnsNull() {
        assertNull(mapper.toEntity(null));
    }
}
