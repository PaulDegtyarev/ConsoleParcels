package ru.liga.console_parcels.factory;

import ru.liga.console_parcels.service.PackagingService;

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
