package com.testeiteam.saw_products_api.dto;

import lombok.Data;

@Data
public class FieldErrorDTO {
    private String field;
    private String message;
}
