package com.testeiteam.saw_products_api.service.impl;

import com.testeiteam.saw_products_api.dto.CategoryResponseDTO;
import com.testeiteam.saw_products_api.model.Category;
import com.testeiteam.saw_products_api.repository.CategoryRepository;
import com.testeiteam.saw_products_api.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId("123e4567-e89b-12d3-a456-426614174000");
        category1.setName("Electronics");
        category1.setCreatedAt(OffsetDateTime.now());
        category1.setUpdatedAt(OffsetDateTime.now());

        category2 = new Category();
        category2.setId("223e4567-e89b-12d3-a456-426614174001");
        category2.setName("Clothes");
        category2.setCreatedAt(OffsetDateTime.now());
        category2.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    void testListAllCategories_Success() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<CategoryResponseDTO> responseDTOList = categoryService.listAllCategories();

        assertNotNull(responseDTOList);
        assertEquals(2, responseDTOList.size());
        assertEquals("Electronics", responseDTOList.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }
}
