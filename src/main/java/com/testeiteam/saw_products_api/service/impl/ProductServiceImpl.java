package com.testeiteam.saw_products_api.service.impl;

import com.testeiteam.saw_products_api.dto.*;
import com.testeiteam.saw_products_api.exception.ResourceNotFoundException;
import com.testeiteam.saw_products_api.model.Product;
import com.testeiteam.saw_products_api.repository.ProductRepository;
import com.testeiteam.saw_products_api.service.ProductService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setCategory(requestDTO.getCategory());
        product.setStock(requestDTO.getStock());
        product.setCreatedAt(OffsetDateTime.now());
        product.setUpdatedAt(OffsetDateTime.now());

        Product savedProduct = productRepository.save(product);
        return toResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        return toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(String productId, ProductRequestDTO requestDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        existingProduct.setName(requestDTO.getName());
        existingProduct.setDescription(requestDTO.getDescription());
        existingProduct.setPrice(requestDTO.getPrice());
        existingProduct.setCategory(requestDTO.getCategory());
        existingProduct.setStock(requestDTO.getStock());
        existingProduct.setUpdatedAt(OffsetDateTime.now());

        Product updatedProduct = productRepository.save(existingProduct);
        return toResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(String productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        productRepository.delete(existingProduct);
    }

    @Override
    public ProductPageResponseDTO listProducts(String category,
                                               String minPrice,
                                               String maxPrice,
                                               Pageable pageable) {

        BigDecimal min = (minPrice != null && !minPrice.isBlank())
                ? new BigDecimal(minPrice)
                : null;
        BigDecimal max = (maxPrice != null && !maxPrice.isBlank())
                ? new BigDecimal(maxPrice)
                : null;

        Page<Product> productPage = productRepository.findAllWithFilters(category, min, max, pageable);

        var content = productPage.getContent().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        ProductPageResponseDTO responseDTO = new ProductPageResponseDTO();
        responseDTO.setContent(content);
        responseDTO.setSize(productPage.getSize());
        responseDTO.setTotalElements(productPage.getTotalElements());
        responseDTO.setTotalPages(productPage.getTotalPages());
        responseDTO.setPageNumber(productPage.getNumber() + 1); // 1-based index

        return responseDTO;
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setStock(product.getStock());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }
}
