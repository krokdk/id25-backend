package com.id25.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.*;
import java.security.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralSecurityException.class)
    public ResponseEntity<String> handleSecurityException(GeneralSecurityException ex) {
        return new ResponseEntity<>("Security error: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        return new ResponseEntity<>("I/O error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("Internal error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
