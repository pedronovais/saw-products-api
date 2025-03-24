package com.testeiteam.saw_products_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class ErrorResponseDTO {
    private String title;
    private int status;
    private String detail;
    private List<FieldErrorDTO> errors;
}
