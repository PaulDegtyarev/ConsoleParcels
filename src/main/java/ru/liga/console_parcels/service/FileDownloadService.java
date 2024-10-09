package ru.liga.console_parcels.service;

import org.springframework.web.multipart.MultipartFile;
import ru.liga.console_parcels.exception.FileDownloadException;

import java.nio.file.Path;

/**
 * Сервис для скачивания файлов.
 */
public interface FileDownloadService {
    /**
     * Скачивает файл и сохраняет его в указанную директорию.
     *
     * @param file МultipartFile, который нужно скачать.
     * @return Path к сохраненному файлу.
     * @throws FileDownloadException если произошла ошибка при скачивании файла.
     */
    Path download(MultipartFile file);
}
