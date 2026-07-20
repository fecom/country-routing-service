package com.pwc.routing.exception;

import com.pwc.routing.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RouteNotFoundException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleRouteNotFound(RuntimeException exception) {
        return ResponseEntity
            .badRequest()
            .body(new ErrorResponse(exception.getMessage()));
    }

}
