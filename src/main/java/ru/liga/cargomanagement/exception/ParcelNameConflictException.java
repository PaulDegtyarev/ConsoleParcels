package ru.liga.cargomanagement.exception;

public class ParcelNameConflictException extends RuntimeException {
    public ParcelNameConflictException(String message) {
        super(message);
    }
}
