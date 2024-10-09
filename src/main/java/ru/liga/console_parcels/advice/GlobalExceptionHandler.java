package ru.liga.console_parcels.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.liga.console_parcels.exception.FileNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException fileNotFoundException) {
        return new ResponseEntity<>(fileNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
