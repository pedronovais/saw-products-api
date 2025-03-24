package com.testeiteam.saw_products_api.service.impl;

import com.testeiteam.saw_products_api.dto.ProductRequestDTO;
import com.testeiteam.saw_products_api.dto.ProductResponseDTO;
import com.testeiteam.saw_products_api.exception.ResourceNotFoundException;
import com.testeiteam.saw_products_api.model.Product;
import com.testeiteam.saw_products_api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;
    private String testProductId;

    @BeforeEach
    void setUp() {
        testProductId = UUID.randomUUID().toString();
        testProduct = new Product();
        testProduct.setId(testProductId);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test product description");
        testProduct.setPrice(new BigDecimal("100.00"));
        testProduct.setCategory("Electronics");
        testProduct.setStock(10);
        testProduct.setCreatedAt(OffsetDateTime.now());
        testProduct.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(testProductId)).thenReturn(Optional.of(testProduct));

        ProductResponseDTO response = productService.getProductById(testProductId);

        assertNotNull(response);
        assertEquals(testProductId, response.getId());
        assertEquals("Test Product", response.getName());
        verify(productRepository, times(1)).findById(testProductId);
    }

    @Test
    void testGetProductById_NotFound() {
        String nonExistingId = UUID.randomUUID().toString();
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productService.getProductById(nonExistingId));
    }

    @Test
    void testCreateProduct_Success() {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("New Product");
        requestDTO.setDescription("New product description");
        requestDTO.setPrice(new BigDecimal("200.00"));
        requestDTO.setCategory("Electronics");
        requestDTO.setStock(5);

        Product savedProduct = new Product();
        savedProduct.setId(UUID.randomUUID().toString());
        savedProduct.setName(requestDTO.getName());
        savedProduct.setDescription(requestDTO.getDescription());
        savedProduct.setPrice(requestDTO.getPrice());
        savedProduct.setCategory(requestDTO.getCategory());
        savedProduct.setStock(requestDTO.getStock());
        savedProduct.setCreatedAt(OffsetDateTime.now());
        savedProduct.setUpdatedAt(OffsetDateTime.now());

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponseDTO responseDTO = productService.createProduct(requestDTO);

        assertNotNull(responseDTO.getId());
        assertEquals("New Product", responseDTO.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_Success() {
        when(productRepository.findById(testProductId)).thenReturn(Optional.of(testProduct));

        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("Updated Name");
        requestDTO.setDescription("Updated description");
        requestDTO.setPrice(new BigDecimal("150.00"));
        requestDTO.setCategory("Electronics");
        requestDTO.setStock(7);

        Product updatedProduct = new Product();
        updatedProduct.setId(testProductId);
        updatedProduct.setName(requestDTO.getName());
        updatedProduct.setDescription(requestDTO.getDescription());
        updatedProduct.setPrice(requestDTO.getPrice());
        updatedProduct.setCategory(requestDTO.getCategory());
        updatedProduct.setStock(requestDTO.getStock());
        updatedProduct.setCreatedAt(testProduct.getCreatedAt());
        updatedProduct.setUpdatedAt(OffsetDateTime.now());

        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        ProductResponseDTO responseDTO = productService.updateProduct(testProductId, requestDTO);

        assertEquals("Updated Name", responseDTO.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_Success() {
        when(productRepository.findById(testProductId)).thenReturn(Optional.of(testProduct));

        productService.deleteProduct(testProductId);

        verify(productRepository, times(1)).delete(testProduct);
    }

    @Test
    void testListProducts_Success() {
        Page<Product> productPage = new PageImpl<>(Arrays.asList(testProduct));
        when(productRepository.findAllWithFilters(anyString(), any(), any(), any(Pageable.class)))
                .thenReturn(productPage);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        var responseDTO = productService.listProducts(null, null, null, pageable);

        assertEquals(1, responseDTO.getContent().size());
        assertEquals(1, responseDTO.getTotalElements());
        assertEquals(1, responseDTO.getTotalPages());
        verify(productRepository, times(1))
                .findAllWithFilters(anyString(), any(), any(), eq(pageable));
    }
}
