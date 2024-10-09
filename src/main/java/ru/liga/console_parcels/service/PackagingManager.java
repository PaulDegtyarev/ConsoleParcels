package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.PackRequestDto;
import ru.liga.console_parcels.entity.Truck;

import java.util.List;

/**
 * Интерфейс для управления упаковкой.
 */
public interface PackagingManager {
    /**
     * Упаковывает посылки согласно запросу.
     *
     * @param packRequestDto Запрос на упаковку.
     * @return Список упакованных грузовиков.
     */
    List<Truck> pack(PackRequestDto packRequestDto);
}
