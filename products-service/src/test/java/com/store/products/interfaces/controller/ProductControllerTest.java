package com.store.products.interfaces.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.PageRequest; // not used
import org.springframework.data.domain.Pageable;
import java.util.List;
import static org.mockito.Mockito.doNothing;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.store.products.application.service.ProductService;
import com.store.products.domain.model.Product;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();

        product = new Product();
        product.setId(1L);
        product.setCode("P-001");
        product.setName("Sample");
    }

    @Test
    void findByCode_ShouldReturnProduct() throws Exception {
        when(productService.findByCode("P-001")).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/P-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("P-001"))
                .andExpect(jsonPath("$.name").value("Sample"));

        verify(productService).findByCode("P-001");
    }

    @Test
    void create_ShouldReturnCreatedProduct() throws Exception {
        when(productService.create(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("P-001"));

        verify(productService).create(any(Product.class));
    }

    @Test
    void update_ShouldReturnUpdatedProduct() throws Exception {
        when(productService.update(eq(1L), any(Product.class))).thenReturn(product);

        mockMvc.perform(patch("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("P-001"));

        verify(productService).update(eq(1L), any(Product.class));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        doNothing().when(productService).delete(2L);

        mockMvc.perform(delete("/api/v1/products/2"))
                .andExpect(status().isNoContent());

        verify(productService).delete(2L);
    }

}
