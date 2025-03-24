package com.testeiteam.saw_products_api.service;

import com.testeiteam.saw_products_api.dto.ProductPageResponseDTO;
import com.testeiteam.saw_products_api.dto.ProductRequestDTO;
import com.testeiteam.saw_products_api.dto.ProductResponseDTO;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    /**
     * @param requestDTO product data from the client
     * @return the created product as a response DTO
     */
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);

    /**
     * @param productId the UUID of the product
     * @return the product as a response DTO
     */
    ProductResponseDTO getProductById(String productId);

    /**
     * @param productId the UUID of the product
     * @param requestDTO updated product data
     * @return the updated product as a response DTO
     */
    ProductResponseDTO updateProduct(String productId, ProductRequestDTO requestDTO);

    /**
     * @param productId the UUID of the product
     */
    void deleteProduct(String productId);

    /**
     * @param category optional category filter
     * @param minPrice optional min price filter
     * @param maxPrice optional max price filter
     * @param pageable Spring Data object for pagination and sorting
     * @return a ProductPageResponseDTO containing paginated products and metadata
     */
    ProductPageResponseDTO listProducts(String category,
                                        String minPrice,
                                        String maxPrice,
                                        Pageable pageable);
}
