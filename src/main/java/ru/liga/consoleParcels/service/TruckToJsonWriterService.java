package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.exception.FileNotFoundException;
import ru.liga.consoleParcels.exception.FileWriteException;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

/**
 * Интерфейс для записи данных о грузовиках в JSON файл.
 */
public interface TruckToJsonWriterService {

    /**
     * Записывает данные о грузовиках в JSON файл.
     *
     * @param trucks   Список грузовиков.
     * @param filePath Путь к файлу для записи.
     * @throws FileNotFoundException Если файл не существует.
     * @throws FileWriteException    Если произошла ошибка при записи файла.
     */
    void writeTruckToJson(List<Truck> trucks, String filePath);
}
