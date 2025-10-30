package com.store.inventory.domain.model;

import java.time.LocalDateTime;

public class Inventory {
    private Long id;
    private String productCode;
    private Integer quantity;
    private LocalDateTime lastUpdated;
    private Integer version;
    
    public Inventory() {
    }
    
    public Inventory(String productCode, Integer quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
        
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}