package com.elpidoroun.MarlowBank.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandling {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandling.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleException(ValidationException validationException){
        logger.error("Exception occurred: " + validationException);

        return ResponseEntity.badRequest().body(validationException.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException entityNotFoundException){
        logger.error("Exception occurred: " + entityNotFoundException);

        return ResponseEntity.badRequest().body(entityNotFoundException.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException entityNotFoundException){
        logger.error("Exception occurred: " + entityNotFoundException);

        return ResponseEntity.badRequest().body(entityNotFoundException.getMessage());
    }

}
