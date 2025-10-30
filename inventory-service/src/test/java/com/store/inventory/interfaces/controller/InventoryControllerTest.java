package com.store.inventory.interfaces.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.inventory.application.service.InventoryService;
import com.store.inventory.domain.model.Inventory;
import com.store.inventory.interfaces.dto.InventoryRequest;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    private Inventory testInventory;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
        testInventory = new Inventory();
        testInventory.setProductCode("TEST001");
        testInventory.setQuantity(10);
        testInventory.setLastUpdated(LocalDateTime.now());
    }

    @Test
    void getInventory_ShouldReturnInventory_WhenProductExists() throws Exception {
        when(inventoryService.findByProductCode("TEST001"))
                .thenReturn(testInventory);

        mockMvc.perform(get("/api/v1/inventories/TEST001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCode").value("TEST001"))
                .andExpect(jsonPath("$.quantity").value(10));

        verify(inventoryService).findByProductCode("TEST001");
    }

    @Test
    void updateStock_ShouldUpdateAndReturnInventory_WhenRequestIsValid() throws Exception {
        InventoryRequest request = new InventoryRequest();
        request.setProductCode("TEST001");
        request.setQuantity(5);

        when(inventoryService.updateStock("TEST001", 5))
                .thenReturn(testInventory);

        mockMvc.perform(patch("/api/v1/inventories/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCode").value("TEST001"))
                .andExpect(jsonPath("$.quantity").value(10));

        verify(inventoryService).updateStock("TEST001", 5);
    }
}