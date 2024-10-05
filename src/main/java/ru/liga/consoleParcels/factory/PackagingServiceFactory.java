package ru.liga.consoleParcels.factory;

import ru.liga.consoleParcels.service.PackagingService;

/**
 * Интерфейс фабрики сервисов упаковки.
 *
 * <p>
 * Интерфейс определяет методы для создания различных сервисов упаковки.
 * </p>
 */
public interface PackagingServiceFactory {
    /**
     * Метод для создания оптимизированного сервиса упаковки.
     *
     * @return Экземпляр {@link PackagingService}, представляющий оптимизированный сервис упаковки.
     */
    PackagingService createOptimizedPackagingService();

    /**
     * Метод для создания сервиса одиночной упаковки.
     *
     * @return Экземпляр {@link PackagingService}, представляющий сервис одиночной упаковки.
     */
    PackagingService createSinglePackagingService();
}
