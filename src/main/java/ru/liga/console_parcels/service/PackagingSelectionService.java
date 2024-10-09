package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.TruckPackageAlgorithm;

/**
 * Интерфейс для выбора сервиса упаковки посылок.
 */
public interface PackagingSelectionService {
    /**
     * Выбирает сервис упаковки на основе выбранного алгоритма.
     *
     * @param algorithmChoice Выбранный алгоритм упаковки.
     * @return Сервис упаковки, соответствующий выбранному алгоритму.
     */
    TruckPackageService selectPackagingService(TruckPackageAlgorithm algorithmChoice);
}
