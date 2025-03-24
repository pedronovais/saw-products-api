package com.testeiteam.saw_products_api.controller;

import com.testeiteam.saw_products_api.dto.CategoryResponseDTO;
import com.testeiteam.saw_products_api.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> listCategories() {
        List<CategoryResponseDTO> categories = categoryService.listAllCategories();
        return ResponseEntity.ok(categories);
    }
}
