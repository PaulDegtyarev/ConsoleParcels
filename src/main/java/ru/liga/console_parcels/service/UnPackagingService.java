package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.UnpackedTruckDto;

import java.util.List;

/**
 * Интерфейс для сервиса распаковки грузовиков.
 */
public interface UnPackagingService {

    /**
     * Распаковывает грузовики из файлов и формирует данные о распаковке.
     *
     * @param truckFilePath       Путь к файлу с данными грузовиков.
     * @return Список DTO с данными о распаковке грузовиков.
     */
    List<UnpackedTruckDto> unpackTruck(String truckFilePath);
}
