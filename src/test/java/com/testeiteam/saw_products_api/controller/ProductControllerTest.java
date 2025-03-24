package com.testeiteam.saw_products_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testeiteam.saw_products_api.dto.ProductPageResponseDTO;
import com.testeiteam.saw_products_api.dto.ProductRequestDTO;
import com.testeiteam.saw_products_api.dto.ProductResponseDTO;
import com.testeiteam.saw_products_api.exception.BadRequestException;
import com.testeiteam.saw_products_api.exception.ResourceNotFoundException;
import com.testeiteam.saw_products_api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateProduct_Unauthorized() throws Exception {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("New Product");
        requestDTO.setDescription("New product description");
        requestDTO.setPrice(new BigDecimal("200.00"));
        requestDTO.setCategory("Electronics");
        requestDTO.setStock(5);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testCreateProduct_AsAdmin_Success() throws Exception {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(UUID.randomUUID().toString());
        responseDTO.setName("Created Product");
        responseDTO.setDescription("Some description");
        responseDTO.setPrice(new BigDecimal("100.00"));
        responseDTO.setCategory("Electronics");
        responseDTO.setStock(10);

        when(productService.createProduct(any(ProductRequestDTO.class))).thenReturn(responseDTO);

        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("Created Product");
        requestDTO.setDescription("Some description");
        requestDTO.setPrice(new BigDecimal("100.00"));
        requestDTO.setCategory("Electronics");
        requestDTO.setStock(10);

        mockMvc.perform(post("/api/products")
                        .with(httpBasic("admin@domain.com", "adminpassword"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Created Product"));
    }

    @Test
    void testGetProductById_BadUUID() throws Exception {
        String invalidUUID = "123-invalid-uuid";

        mockMvc.perform(get("/api/products/{id}", invalidUUID)
                        .with(httpBasic("admin@domain.com", "adminpassword")))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        String validUUID = UUID.randomUUID().toString();

        when(productService.getProductById(validUUID))
                .thenThrow(new ResourceNotFoundException("Product not found with id: " + validUUID));

        mockMvc.perform(get("/api/products/{id}", validUUID)
                        .with(httpBasic("admin@domain.com", "adminpassword")))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchProducts_AsUser_Success() throws Exception {
        ProductPageResponseDTO pageResponse = new ProductPageResponseDTO();
        pageResponse.setContent(Arrays.asList(new ProductResponseDTO()));
        pageResponse.setSize(1);
        pageResponse.setTotalElements(1L);
        pageResponse.setTotalPages(1);
        pageResponse.setPageNumber(1);

        when(productService.listProducts(anyString(), anyString(), anyString(), any()))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/api/products")
                        .with(httpBasic("user@domain.com", "userpassword"))
                        .param("category", "Electronics")
                        .param("minPrice", "100")
                        .param("maxPrice", "1000")
                        .param("sortBy", "name")
                        .param("order", "asc")
                        .param("page", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk());
    }
}
