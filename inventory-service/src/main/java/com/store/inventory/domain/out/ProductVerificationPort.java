package com.store.inventory.domain.out;

public interface ProductVerificationPort {
    /**
     * Define la operación para verificar si un producto existe por su código.
     * Esta es la interfaz que usará la capa de Application.
     */
    boolean isProductExist(String productCode);

}
