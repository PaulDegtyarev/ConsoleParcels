package ru.liga.consoleParcels.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.consoleParcels.exception.FileDownloadException;
import ru.liga.consoleParcels.service.FileDownloadService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DefaultFileDownloadService implements FileDownloadService {
    @Override
    public Path download(MultipartFile file) {
        Path path = Paths.get("download/" + file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (IOException e) {
            throw new FileDownloadException("Ошибка в скачивании файла: " + file.getOriginalFilename());
        }
    }
}
