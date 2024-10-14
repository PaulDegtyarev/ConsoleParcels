package ru.liga.cargomanagement.service;

import ru.liga.cargomanagement.dto.ParcelForPackagingDto;
import ru.liga.cargomanagement.exception.FileNotFoundException;

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
