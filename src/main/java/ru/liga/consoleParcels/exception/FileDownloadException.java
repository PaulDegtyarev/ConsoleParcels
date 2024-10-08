package ru.liga.consoleParcels.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileDownloadException extends ResponseStatusException {
    public FileDownloadException(String message) {super(HttpStatus.BAD_REQUEST, message);}
}
