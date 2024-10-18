package ru.liga.cargomanagement.exception;

public class WrongMessageFormatException extends RuntimeException {
    public WrongMessageFormatException(String message) {
        super(message);
    }
}
