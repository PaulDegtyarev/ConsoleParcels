package ru.liga.cargomanagement.service;

import ru.liga.cargomanagement.dto.ParcelForPackagingDto;
import ru.liga.cargomanagement.entity.Truck;

import java.util.List;

/**
 * Интерфейс для упаковки посылок в грузовики.
 * Определяет методы для упаковки посылок в грузовики заданного размера.
 */
public interface TruckPackageService {
    /**
     * Упаковывает посылки в грузовики.
     *
     * @param parcels Список посылок для упаковки
     */
    List<Truck> packPackages(List<ParcelForPackagingDto> parcels, String trucks);
}
