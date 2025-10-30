package com.store.products.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void gettersAndSetters() {
        Product p = new Product();
        p.setId(2L);
        p.setCode("T1");
        p.setName("N");
        p.setDescription("D");
        p.setPrice(BigDecimal.valueOf(9.99));
        p.setActive(true);
        LocalDateTime now = LocalDateTime.now();
        p.setCreatedAt(now);
        p.setUpdatedAt(now);

        assertEquals(2L, p.getId());
        assertEquals("T1", p.getCode());
        assertEquals("N", p.getName());
        assertEquals("D", p.getDescription());
        assertEquals(0, BigDecimal.valueOf(9.99).compareTo(p.getPrice()));
        assertTrue(p.getActive());
        assertEquals(now, p.getCreatedAt());
        assertEquals(now, p.getUpdatedAt());
    }
}
