package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.TruckParcelCountDto;

import java.util.List;

/**
 * Интерфейс для записи.
 */
public interface RecordingService {

    /**
     * Записывает количество посылок.
     *
     * @param truckParcelCounts Список DTO с данными о количестве посылок в каждом грузовике.
     */
    void write(List<TruckParcelCountDto> truckParcelCounts);
}
