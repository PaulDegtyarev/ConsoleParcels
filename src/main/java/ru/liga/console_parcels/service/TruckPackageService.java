package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.ParcelForPackagingDto;
import ru.liga.console_parcels.model.Truck;

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
