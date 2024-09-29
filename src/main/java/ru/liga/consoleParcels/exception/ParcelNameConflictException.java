package ru.liga.consoleParcels.exception;

public class ParcelNameConflictException extends RuntimeException {
    public ParcelNameConflictException(String message) {
        super(message);
    }
}
