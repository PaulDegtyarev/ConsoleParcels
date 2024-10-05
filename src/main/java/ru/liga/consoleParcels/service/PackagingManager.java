package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.PackRequestDto;

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
