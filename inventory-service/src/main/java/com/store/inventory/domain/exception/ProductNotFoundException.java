package com.store.inventory.domain.exception;

/**
 * Excepción lanzada cuando un producto no puede ser verificado/encontrado 
 * en el microservicio Products Service.
 */
public class ProductNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String productCode) {
        super("El producto no puede ser encontrado en el Product Service con codigo: " + productCode);
    }
}