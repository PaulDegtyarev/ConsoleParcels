package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.exception.FileWriteException;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

/**
 * Сервис для записи данных о грузовиках в JSON файл.
 *
 * Этот интерфейс определяет метод для записи списка
 * грузовиков в JSON файл по заданному пути.
 */
public interface TruckToJsonWriterService {
    /**
     * Записывает данные о грузовиках в JSON файл.
     *
     * @param trucks   Список грузовиков, данные о которых
     *                 нужно записать.
     * @param filePath Путь к файлу, в который нужно записать
     *                 данные.
     * @throws FileWriteException Если произошла ошибка при
     *                           записи в файл.
     */
    void writeTruckToJson(List<Truck> trucks, String filePath);
}
