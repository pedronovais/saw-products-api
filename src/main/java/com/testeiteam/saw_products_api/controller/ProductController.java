package com.testeiteam.saw_products_api.controller;

import com.testeiteam.saw_products_api.dto.ProductPageResponseDTO;
import com.testeiteam.saw_products_api.dto.ProductRequestDTO;
import com.testeiteam.saw_products_api.dto.ProductResponseDTO;
import com.testeiteam.saw_products_api.exception.BadRequestException;
import com.testeiteam.saw_products_api.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private final ProductService productService;

    // Campos válidos para ordenação
    private static final String[] VALID_SORT_FIELDS = { "name", "price", "createdAt" };

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO requestDTO
    ) {
        ProductResponseDTO createdProduct = productService.createProduct(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable String id) {
        if (!isValidUUID(id)) {
            throw new BadRequestException("Invalid UUID format: " + id);
        }
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody ProductRequestDTO requestDTO
    ) {
        if (!isValidUUID(id)) {
            throw new BadRequestException("Invalid UUID format: " + id);
        }
        ProductResponseDTO updatedProduct = productService.updateProduct(id, requestDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        if (!isValidUUID(id)) {
            throw new BadRequestException("Invalid UUID format: " + id);
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ProductPageResponseDTO> searchProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String minPrice,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        // Validar sortBy
        if (!isValidSortField(sortBy)) {
            throw new BadRequestException("Invalid 'sortBy' value: " + sortBy
                    + ". Allowed values: name, price, createdAt");
        }

        int pageIndex = (page < 1) ? 0 : page - 1;
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(order)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        ProductPageResponseDTO response = productService.listProducts(category, minPrice, maxPrice, pageable);
        return ResponseEntity.ok(response);
    }

    private boolean isValidUUID(String id) {
        try {
            UUID.fromString(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidSortField(String sortBy) {
        for (String field : VALID_SORT_FIELDS) {
            if (field.equalsIgnoreCase(sortBy)) {
                return true;
            }
        }
        return false;
    }
}
