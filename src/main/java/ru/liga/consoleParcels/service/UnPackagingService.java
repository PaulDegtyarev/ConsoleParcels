package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.exception.FileReadException;

import java.util.List;

/**
 * Сервис для распаковки грузовиков из JSON файла.
 * <p>
 * Этот интерфейс определяет метод для распаковки данных
 * о грузовиках из JSON файла и возвращает список
 * объектов {@link UnPackedTruckDto}, представляющих
 * информацию о каждом грузовике.
 */
public interface UnPackagingService {
    /**
     * Распаковывает данные о грузовиках из JSON файла.
     *
     * @param filePath Путь к файлу JSON, содержащему данные
     *                 о грузовиках.
     * @return Список объектов {@link UnPackedTruckDto},
     * представляющих информацию о каждом
     * распакованном грузовике.
     * @throws FileReadException Если произошла ошибка при
     *                           чтении файла.
     */
    List<UnPackedTruckDto> unpackTruck(String filePath);
}
