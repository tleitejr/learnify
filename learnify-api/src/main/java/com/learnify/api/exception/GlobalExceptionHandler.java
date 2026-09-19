/*
 * Copyright (c) 2026 Antonio C. Leite Jr
 *
 * Learnify
 *
 * Final Year Project (Academic Capstone)
 * Bachelor in Software Engineering
 *
 * All rights reserved.
 */

package com.learnify.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler for the application.
 *
 * <p>Centralizes exception handling across all controllers, providing consistent
 * error responses in the {@link ApiError} format. Each exception type is mapped
 * to an appropriate HTTP status and error message.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ResourceNotFoundException} (HTTP 404).
     *
     * <p>Returned when a requested resource (e.g., user, content, question)
     * is not found in the database.
     *
     * @param ex      the exception instance
     * @param request the HTTP request
     * @return a {@link ResponseEntity} containing an {@link ApiError} with status 404
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles {@link BusinessException} (HTTP 400).
     *
     * <p>Returned when a business rule is violated, such as duplicate email,
     * already answered question, or invalid operation.
     *
     * @param ex      the exception instance
     * @param request the HTTP request
     * @return a {@link ResponseEntity} containing an {@link ApiError} with status 400
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(
            BusinessException ex,
            HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Business Error",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles {@link UnauthorizedException} (HTTP 401).
     *
     * <p>Returned when authentication fails, such as invalid credentials,
     * expired token, or attempting to perform an action without proper login.
     *
     * @param ex      the exception instance
     * @param request the HTTP request
     * @return a {@link ResponseEntity} containing an {@link ApiError} with status 401
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    /**
     * Handles any unhandled exception (HTTP 500).
     *
     * <p>Acts as a fallback for unexpected system errors that are not explicitly
     * caught by other handlers. Returns a generic error message to avoid
     * exposing internal details.
     *
     * @param ex      the exception instance
     * @param request the HTTP request
     * @return a {@link ResponseEntity} containing an {@link ApiError} with status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Erro inesperado no sistema.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Handles validation errors from {@link jakarta.validation.Valid} annotated
     * request bodies (HTTP 400).
     *
     * <p>Extracts the first field validation error message from the binding result
     * and returns it in a user-friendly format.
     *
     * @param ex      the exception containing validation errors
     * @param request the HTTP request
     * @return a {@link ResponseEntity} containing an {@link ApiError} with status 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação!");

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
