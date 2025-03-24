package com.testeiteam.saw_products_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testeiteam.saw_products_api.dto.CategoryResponseDTO;
import com.testeiteam.saw_products_api.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testListAllCategories_Success() throws Exception {
        CategoryResponseDTO category1 = new CategoryResponseDTO();
        category1.setId("123e4567-e89b-12d3-a456-426614174000");
        category1.setName("Electronics");
        category1.setCreatedAt(OffsetDateTime.now());
        category1.setUpdatedAt(OffsetDateTime.now());

        CategoryResponseDTO category2 = new CategoryResponseDTO();
        category2.setId("223e4567-e89b-12d3-a456-426614174001");
        category2.setName("Clothes");
        category2.setCreatedAt(OffsetDateTime.now());
        category2.setUpdatedAt(OffsetDateTime.now());

        List<CategoryResponseDTO> categories = Arrays.asList(category1, category2);
        when(categoryService.listAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("123e4567-e89b-12d3-a456-426614174000")))
                .andExpect(jsonPath("$[0].name", is("Electronics")))
                .andExpect(jsonPath("$[1].id", is("223e4567-e89b-12d3-a456-426614174001")))
                .andExpect(jsonPath("$[1].name", is("Clothes")));
    }
}
