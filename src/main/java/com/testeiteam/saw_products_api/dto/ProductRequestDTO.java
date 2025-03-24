package com.testeiteam.saw_products_api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "The 'name' field is required.")
    @Size(min = 3, max = 100, message = "The 'name' field must be between 3 and 100 characters.")
    private String name;

    @NotBlank(message = "The 'description' field is required.")
    @Size(min = 10, max = 500, message = "The 'description' field must be between 10 and 500 characters.")
    private String description;

    @NotNull(message = "The 'price' field is required.")
    @DecimalMin(value = "0.01", message = "The 'price' must be at least 0.01.")
    @DecimalMax(value = "999999.99", message = "The 'price' must be at most 999999.99.")
    private BigDecimal price;

    @NotBlank(message = "The 'category' field is required.")
    private String category;

    @NotNull(message = "The 'stock' field is required.")
    @Min(value = 0, message = "The 'stock' must be at least 0.")
    @Max(value = 10000, message = "The 'stock' must be at most 10000.")
    private Integer stock;
}
