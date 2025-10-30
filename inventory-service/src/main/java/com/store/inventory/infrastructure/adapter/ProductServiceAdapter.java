package com.store.inventory.infrastructure.adapter;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.store.inventory.domain.out.ProductVerificationPort;

@Component
public class ProductServiceAdapter implements ProductVerificationPort {

    private final WebClient webClient;
    private final String PRODUCTS_SERVICE_URL = "http://products-service:8080/api/v1/products/";

    public ProductServiceAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public boolean isProductExist(String productCode) {
        try {
            this.webClient.get()
                    .uri(PRODUCTS_SERVICE_URL + "{productCode}", productCode)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            return true;

        } catch (WebClientResponseException.NotFound notFoundException) {
            // Manejo específico del error 404.
            return false;

        } catch (Exception e) {
            System.err.println("Error al verificar producto con Products Service: " + e.getMessage());
            return false;
        }
    }
}