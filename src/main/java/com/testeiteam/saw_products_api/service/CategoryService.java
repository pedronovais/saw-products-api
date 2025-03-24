package com.testeiteam.saw_products_api.service;

import com.testeiteam.saw_products_api.dto.CategoryResponseDTO;
import java.util.List;

public interface CategoryService {
    /**
     * @return a list of CategoryResponseDTO
     */
    List<CategoryResponseDTO> listAllCategories();
}
