package com.manager.smbuilding.presentation.exception;

import com.manager.smbuilding.application.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCredentialsException(InvalidCredentialsException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>>handleResourceNotFoundException(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(AccessDinedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDinedException(AccessDinedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(FileDeletionException.class)
    public ResponseEntity<Map<String, String>> handleFileDeletionException(FileDeletionException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<Map<String, String>> handleEmptyFileException(EmptyFileException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(InvalidDocumentTypeException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDocumentTypeException(InvalidDocumentTypeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(AmountOverPriceException.class)
    public ResponseEntity<Map<String, String>> handleAmountOverPriceException(AmountOverPriceException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Amount over price");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

}
