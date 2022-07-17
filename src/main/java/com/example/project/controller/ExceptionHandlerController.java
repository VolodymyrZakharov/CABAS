package com.example.project.controller;

import com.example.project.dto.ErrorResponseDTO;
import com.example.project.dto.InputValidationResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleErrorResponse(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorResponseDTO.builder()
                        .message(ex.getReason())
                        .status(ex.getStatus())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InputValidationResponseDTO> handleInputError(MethodArgumentNotValidException ex) {

        var responseBody = ex.getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                DefaultMessageSourceResolvable::getDefaultMessage,
                                Collectors.toList()
                        )
                ));

        InputValidationResponseDTO response = InputValidationResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errors(responseBody)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);

    }
}
