package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.TruckParcelCountDto;

import java.util.List;

/**
 * Интерфейс для записи количества посылок в JSON файл.
 */
public interface ParcelQuantityRecordingService {

    /**
     * Записывает количество посылок в JSON файл.
     *
     * @param truckParcelCounts Список DTO с данными о количестве посылок в каждом грузовике.
     */
    void writeParcelCountToJsonFile(List<TruckParcelCountDto> truckParcelCounts);
}
