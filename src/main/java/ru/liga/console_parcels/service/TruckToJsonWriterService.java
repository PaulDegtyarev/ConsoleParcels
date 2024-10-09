package ru.liga.console_parcels.service;

import ru.liga.console_parcels.exception.FileNotFoundException;
import ru.liga.console_parcels.exception.FileWriteException;
import ru.liga.console_parcels.model.Truck;

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
