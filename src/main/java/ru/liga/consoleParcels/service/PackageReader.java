package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.ParcelForPackagingDto;

import java.util.List;

/**
 * Интерфейс для чтения посылок из файла.
 */
public interface PackageReader {
    /**
     * Читает посылки из файла.
     *
     * @param filename Имя файла с данными посылок.
     * @return Список посылок для упаковки.
     * @throws ru.liga.consoleParcels.exception.FileNotFoundException Если произошла ошибка при чтении файла.
     */
    List<ParcelForPackagingDto> readPackages(String filename);
}
