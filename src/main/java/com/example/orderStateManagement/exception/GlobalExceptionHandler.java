package com.example.orderStateManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String,Object>> orderNotFoundException(OrderNotFoundException exception){

        Map<String,Object> response = new HashMap<>();

        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message",exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(InvalidTransitionException.class)
    public ResponseEntity<Map<String,Object>> invalidTransitionException(InvalidTransitionException exception){

        Map<String,Object> response = new HashMap<>();

        response.put("status",HttpStatus.NOT_ACCEPTABLE.value());
        response.put("message",exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(response);

    }
}
