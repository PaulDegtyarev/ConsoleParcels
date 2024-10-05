package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.UnPackedTruckDto;

import java.util.List;

/**
 * Интерфейс для сервиса распаковки грузовиков.
 */
public interface UnPackagingService {

    /**
     * Распаковывает грузовики из файлов и формирует данные о распаковке.
     *
     * @param truckFilePath       Путь к файлу с данными грузовиков.
     * @param parcelCountFilePath Путь к файлу с данными о количестве посылок.
     * @return Список DTO с данными о распаковке грузовиков.
     */
    List<UnPackedTruckDto> unpackTruck(String truckFilePath, String parcelCountFilePath);
}
