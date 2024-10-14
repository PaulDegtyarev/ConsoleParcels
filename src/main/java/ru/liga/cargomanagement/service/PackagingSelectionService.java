package ru.liga.cargomanagement.service;

import ru.liga.cargomanagement.dto.TruckPackageAlgorithm;

/**
 * Интерфейс, который выбирает сервис для упаковки посылок.
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
