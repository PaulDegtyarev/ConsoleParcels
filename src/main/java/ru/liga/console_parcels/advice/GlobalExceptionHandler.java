package ru.liga.console_parcels.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.liga.console_parcels.exception.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException fileNotFoundException) {
        return new ResponseEntity<>(fileNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileDownloadException.class)
    public ResponseEntity<String> handleFileDownloadException(FileDownloadException fileDownloadException) {
        return new ResponseEntity<>(fileDownloadException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileReadException.class)
    public ResponseEntity<String> handleFileReadException(FileReadException fileReadException) {
        return new ResponseEntity<>(fileReadException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileWriteException.class)
    public ResponseEntity<String> handleFileWriteException(FileWriteException fileWriteException) {
        return new ResponseEntity<>(fileWriteException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCharacterException.class)
    public ResponseEntity<String> handleInvalidCharacterException(InvalidCharacterException invalidCharacterException) {
        return new ResponseEntity<>(invalidCharacterException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidShapeException.class)
    public ResponseEntity<String> handleInvalidShapeException(InvalidShapeException invalidShapeException) {
        return new ResponseEntity<>(invalidShapeException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PackingException.class)
    public ResponseEntity<String> handlePackingException(PackingException packingException) {
        return new ResponseEntity<>(packingException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParcelNameConflictException.class)
    public ResponseEntity<String> handleParcelNameConflictException(ParcelNameConflictException parcelNameConflictException) {
        return new ResponseEntity<>(parcelNameConflictException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ParcelNotFoundException.class)
    public ResponseEntity<String> handleParcelNotFoundException(ParcelNotFoundException parcelNotFoundException) {
        return new ResponseEntity<>(parcelNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongSymbolInShapeException.class)
    public ResponseEntity<String> handleWrongSymbolInShapeException(WrongSymbolInShapeException wrongSymbolInShapeException) {
        return new ResponseEntity<>(wrongSymbolInShapeException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
