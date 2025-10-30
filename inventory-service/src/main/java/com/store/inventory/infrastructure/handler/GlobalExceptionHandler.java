package com.store.inventory.infrastructure.handler;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.store.inventory.domain.exception.InventoryNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones cuando no se encuentra un recurso de inventario.
     * Mapea InventoryNotFoundException a una respuesta HTTP 404 NOT FOUND.
     */
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(InventoryNotFoundException ex) {
        
        // 1. Define el cuerpo de la respuesta personalizada
        Map<String, Object> body = Map.of(
            "timestamp", LocalDateTime.now(),
            "status", HttpStatus.NOT_FOUND.value(),
            "error", HttpStatus.NOT_FOUND.getReasonPhrase(), // "Not Found"
            "message", ex.getMessage() // Obtiene el mensaje personalizado de la excepción
        );

        // 2. Devuelve la respuesta con el estado 404
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    
    // Aquí puedes agregar otros @ExceptionHandler para otras excepciones
    // ej: @ExceptionHandler(ProductCodeInvalidException.class) para un 400 BAD REQUEST

}