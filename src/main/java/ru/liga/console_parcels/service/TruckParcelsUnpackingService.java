package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.UnpackedTruckDto;

import java.util.List;

/**
 * Интерфейс для управления распаковкой грузовиков.
 */
public interface TruckParcelsUnpackingService {
    /**
     * Распаковывает посылки из файла грузовика.
     *
     * @param truckFilePath Путь к файлу грузовика, содержащему данные о посылках.
     * @return Список DTO с распакованными данными о посылках.
     */
    List<UnpackedTruckDto> unpack(String truckFilePath);
}
