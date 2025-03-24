package com.testeiteam.saw_products_api.service.impl;

import com.testeiteam.saw_products_api.dto.CategoryResponseDTO;
import com.testeiteam.saw_products_api.model.Category;
import com.testeiteam.saw_products_api.repository.CategoryRepository;
import com.testeiteam.saw_products_api.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDTO> listAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private CategoryResponseDTO toResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }
}
