package com.testeiteam.saw_products_api.exception;

import com.testeiteam.saw_products_api.dto.ErrorResponseDTO;
import com.testeiteam.saw_products_api.dto.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(BadRequestException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setTitle("Bad Request");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setDetail(ex.getMessage());
        errorResponse.setErrors(null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldErrorDTO> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    FieldErrorDTO fieldError = new FieldErrorDTO();
                    fieldError.setField(error.getField());
                    fieldError.setMessage(error.getDefaultMessage());
                    return fieldError;
                })
                .collect(Collectors.toList());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setTitle("Validation Errors");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setDetail("One or more fields are invalid");
        errorResponse.setErrors(fieldErrors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setTitle("Resource Not Found");
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setDetail(ex.getMessage());
        errorResponse.setErrors(null);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setTitle("Internal Server Error");
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setDetail("An unexpected error occurred.");
        errorResponse.setErrors(null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
