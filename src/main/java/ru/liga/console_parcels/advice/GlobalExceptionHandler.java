package ru.liga.console_parcels.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.liga.console_parcels.exception.*;

/**
 * Обработчик исключений для REST-контроллеров.
 * Предоставляет методы для обработки различных исключений,
 * возникающих в процессе работы приложения.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Обрабатывает исключение FileNotFoundException.
     *
     * @param fileNotFoundException исключение, которое нужно обработать
     * @return объект ResponseEntity с сообщением об ошибке и статусом 404 (NOT_FOUND)
     */
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException fileNotFoundException) {
        return new ResponseEntity<>(fileNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает исключение FileDownloadException.
     *
     * @param fileDownloadException исключение, которое нужно обработать
     * @return объект ResponseEntity с сообщением об ошибке и статусом 404 (NOT_FOUND)
     */
    @ExceptionHandler(FileDownloadException.class)
    public ResponseEntity<String> handleFileDownloadException(FileDownloadException fileDownloadException) {
        return new ResponseEntity<>(fileDownloadException.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает исключение FileReadException.
     *
     * @param fileReadException исключение, которое нужно обработать
     * @return объект ResponseEntity с сообщением об ошибке и статусом 400 (BAD_REQUEST)
     */
    @ExceptionHandler(FileReadException.class)
    public ResponseEntity<String> handleFileReadException(FileReadException fileReadException) {
        return new ResponseEntity<>(fileReadException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение FileWriteException.
     *
     * @param fileWriteException исключение, которое нужно обработать
     * @return объект ResponseEntity с сообщением об ошибке и статусом 400 (BAD_REQUEST)
     */
    @ExceptionHandler(FileWriteException.class)
    public ResponseEntity<String> handleFileWriteException(FileWriteException fileWriteException) {
        return new ResponseEntity<>(fileWriteException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение InvalidCharacterException.
     *
     * @param invalidCharacterException исключение, которое нужно обработать
     * @return объект ResponseEntity с сообщением об ошибке и статусом 400 (BAD_REQUEST)
     */
    @ExceptionHandler(InvalidCharacterException.class)
    public ResponseEntity<String> handleInvalidCharacterException(InvalidCharacterException invalidCharacterException) {
        return new ResponseEntity<>(invalidCharacterException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение InvalidShapeException.
     *
     * @param invalidShapeException исключение, которое нужно обработать
     * @return объект ResponseEntity с сообщением об ошибке и статусом 400 (BAD_REQUEST)
     */
    @ExceptionHandler(InvalidShapeException.class)
    public ResponseEntity<String> handleInvalidShapeException(InvalidShapeException invalidShapeException) {
        return new ResponseEntity<>(invalidShapeException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение PackingException.
     *
     * @param packingException исключение, которое нужно обработать
     * @return объект ResponseEntity с сообщением об ошибке и статусом 400 (BAD_REQUEST)
     */
    @ExceptionHandler(PackingException.class)
    public ResponseEntity<String> handlePackingException(PackingException packingException) {
        return new ResponseEntity<>(packingException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение ParcelNameConflictException.
     *
     * @param parcelNameConflictException исключение, которое нужно обработать
     * @return объект ResponseEntity с сообщением об ошибке и статусом 409 (CONFLICT)
     */
    @ExceptionHandler(ParcelNameConflictException.class)
    public ResponseEntity<String> handleParcelNameConflictException(ParcelNameConflictException parcelNameConflictException) {
        return new ResponseEntity<>(parcelNameConflictException.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Обрабатывает исключение ParcelNotFoundException.
     *
     * @param parcelNotFoundException исключение, которое нужно обработать
     * @return объект ResponseEntity с сообщением об ошибке и статусом 404 (NOT_FOUND)
     */
    @ExceptionHandler(ParcelNotFoundException.class)
    public ResponseEntity<String> handleParcelNotFoundException(ParcelNotFoundException parcelNotFoundException) {
        return new ResponseEntity<>(parcelNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает исключение WrongSymbolInShapeException.
     *
     * @param wrongSymbolInShapeException исключение, которое нужно обработать
     * @return объект ResponseEntity с сообщением об ошибке и статусом 400 (BAD_REQUEST)
     */
    @ExceptionHandler(WrongSymbolInShapeException.class)
    public ResponseEntity<String> handleWrongSymbolInShapeException(WrongSymbolInShapeException wrongSymbolInShapeException) {
        return new ResponseEntity<>(wrongSymbolInShapeException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}