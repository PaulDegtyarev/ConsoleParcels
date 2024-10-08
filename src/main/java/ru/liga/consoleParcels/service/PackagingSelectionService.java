package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.model.TruckPackageAlgorithm;

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
    PackagingService selectPackagingService(TruckPackageAlgorithm algorithmChoice);
}
