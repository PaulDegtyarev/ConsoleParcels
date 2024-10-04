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

    List<UnPackedTruckDto> unpackTruck(String truckFilePath, String parcelCountFilePath);
}
