package ru.liga.consoleParcels.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileDownloadService {
    Path download(MultipartFile file);
}
