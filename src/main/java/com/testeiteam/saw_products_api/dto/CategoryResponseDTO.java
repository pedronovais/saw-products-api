package com.testeiteam.saw_products_api.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CategoryResponseDTO {
    private String id;
    private String name;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
