package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.PackRequestDto;
import ru.liga.console_parcels.entity.Truck;

import java.util.List;

/**
 * Интерфейс для управления упаковкой посылок.
 */
public interface PackagingManager {
    List<Truck> packParcels(PackRequestDto packRequestDto);
    /**
     * Упаковывает посылки согласно запросу.
     *
     * @param packRequestDto Запрос на упаковку.
     * @return Строка с результатами упаковки.
     */

}
