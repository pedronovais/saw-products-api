package com.testeiteam.saw_products_api.repository.custom;

import com.testeiteam.saw_products_api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductRepositoryCustom {

    /**
     * @param category Optional category filter.
     * @param minPrice Optional minimum price filter.
     * @param maxPrice Optional maximum price filter.
     * @param pageable Object that contains pagination and sorting information.
     * @return A Page<Product> with filtered and paginated results.
     */
    Page<Product> findAllWithFilters(String category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
