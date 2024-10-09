package ru.liga.console_parcels.exception;

public class ParcelNameConflictException extends RuntimeException {
    public ParcelNameConflictException(String message) {
        super(message);
    }
}
