package com.testeiteam.saw_products_api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class ProductResponseDTO {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stock;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
