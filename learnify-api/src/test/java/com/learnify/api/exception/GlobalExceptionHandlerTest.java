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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private MethodArgumentNotValidException validationException;

    @Mock
    private BindingResult bindingResult;

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleResourceNotFound() {
        
        when(request.getRequestURI()).thenReturn("/users/1");

        
        ResponseEntity<ApiError> result = handler.handleNotFound(new ResourceNotFoundException("missing"), request);

        
        assertEquals(404, result.getStatusCode().value());
        assertEquals("Resource Not Found", result.getBody().error());
        assertEquals("missing", result.getBody().message());
        assertEquals("/users/1", result.getBody().path());
    }

    @Test
    void shouldHandleBusinessAndUnauthorizedErrors() {
        
        when(request.getRequestURI()).thenReturn("/action");

        
        ResponseEntity<ApiError> business = handler.handleBusiness(new BusinessException("invalid"), request);
        ResponseEntity<ApiError> unauthorized = handler.handleUnauthorized(new UnauthorizedException("denied"), request);

        
        assertEquals(400, business.getStatusCode().value());
        assertEquals("Business Error", business.getBody().error());
        assertEquals(401, unauthorized.getStatusCode().value());
        assertEquals("Unauthorized", unauthorized.getBody().error());
    }

    @Test
    void shouldHandleUnexpectedErrorWithoutExposingDetails() {
        
        when(request.getRequestURI()).thenReturn("/action");

        
        ResponseEntity<ApiError> result = handler.handleGeneric(new IllegalStateException("secret"), request);

        
        assertEquals(500, result.getStatusCode().value());
        assertEquals("Internal Server Error", result.getBody().error());
        assertEquals("Erro inesperado no sistema.", result.getBody().message());
    }

    @Test
    void shouldHandleValidationErrorWithFirstFieldMessage() {
        when(request.getRequestURI()).thenReturn("/users");
        when(validationException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(
                java.util.List.of(new FieldError("usuarioRequest", "email", "deve ser um e-mail válido")));

        ResponseEntity<ApiError> result = handler.handleValidation(validationException, request);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Validation Error", result.getBody().error());
        assertEquals("email: deve ser um e-mail válido", result.getBody().message());
        assertEquals("/users", result.getBody().path());
    }

    @Test
    void shouldUseDefaultMessageWhenValidationHasNoFieldErrors() {
        when(request.getRequestURI()).thenReturn("/users");
        when(validationException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(java.util.List.of());

        ResponseEntity<ApiError> result = handler.handleValidation(validationException, request);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Erro de validação!", result.getBody().message());
    }
}