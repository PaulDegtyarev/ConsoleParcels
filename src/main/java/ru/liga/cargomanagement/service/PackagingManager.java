package ru.liga.cargomanagement.service;

import ru.liga.cargomanagement.dto.PackRequestDto;
import ru.liga.cargomanagement.entity.Truck;

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
