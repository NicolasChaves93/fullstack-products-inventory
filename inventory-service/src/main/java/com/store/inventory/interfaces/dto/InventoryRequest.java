package com.store.inventory.interfaces.dto;

public class InventoryRequest {
    private String productCode;
    private Integer quantity;
    
    // getters / setters
    public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
    
}
