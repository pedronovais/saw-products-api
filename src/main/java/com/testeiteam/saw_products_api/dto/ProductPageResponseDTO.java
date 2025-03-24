package com.testeiteam.saw_products_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductPageResponseDTO {
    private List<ProductResponseDTO> content;
    private int size;
    private long totalElements;
    private int totalPages;
    private int pageNumber;
}
