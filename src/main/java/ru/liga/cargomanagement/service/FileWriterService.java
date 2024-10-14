package ru.liga.cargomanagement.service;

import ru.liga.cargomanagement.entity.Truck;
import ru.liga.cargomanagement.exception.FileNotFoundException;
import ru.liga.cargomanagement.exception.FileWriteException;

import java.util.List;

/**
 * Интерфейс для записи данных в файл.
 */
public interface FileWriterService {

    /**
     * Записывает данные о грузовиках в файл.
     *
     * @param trucks   Список грузовиков.
     * @param filePath Путь к файлу для записи.
     * @throws FileNotFoundException Если файл не существует.
     * @throws FileWriteException    Если произошла ошибка при записи файла.
     */
    void write(List<Truck> trucks, String filePath);
}
