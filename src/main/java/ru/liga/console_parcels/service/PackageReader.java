package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.ParcelForPackagingDto;
import ru.liga.console_parcels.exception.FileNotFoundException;

import java.util.List;

/**
 * Интерфейс для чтения посылок из файла.
 */
public interface PackageReader {
    /**
     * Читает посылки из файла.
     *
     * @param filename Имя файла с данными посылок.
     * @return Список посылок.
     * @throws FileNotFoundException Если произошла ошибка при чтении файла.
     */
    List<ParcelForPackagingDto> read(String filename);
}
