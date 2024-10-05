package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

/**
 * Интерфейс для упаковки посылок в грузовики.
 * Определяет методы для упаковки посылок в грузовики заданного размера.
 */
public interface PackagingService {
    /**
     * Упаковывает посылки в грузовики.
     *
     * @param parcels Список посылок для упаковки
     */
    List<Truck> packPackages(List<ParcelForPackagingDto> parcels, String trucks);
}
