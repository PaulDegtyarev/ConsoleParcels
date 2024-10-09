package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.PackRequestDto;

/**
 * Интерфейс для управления упаковкой посылок.
 */
public interface PackagingManager {
    /**
     * Упаковывает посылки согласно запросу.
     *
     * @param packRequestDto Запрос на упаковку.
     * @return Строка с результатами упаковки.
     */
    String packParcels(PackRequestDto packRequestDto);
}
