package ru.liga.consoleParcels.factory;

import ru.liga.consoleParcels.service.PackagingService;

/**
 * Фабрика для создания сервисов упаковки.
 *
 * Этот интерфейс предоставляет методы для создания различных
 * реализаций сервисов упаковки, таких как оптимизированная
 * упаковка и упаковка с равномерной загрузкой.
 */
public interface PackagingServiceFactory {
    /**
     * Создает сервис для оптимизированной упаковки.
     *
     * @return Сервис оптимизированной упаковки.
     */
    PackagingService createOptimizedPackagingService();

    /**
     * Создает сервис для упаковки с равномерной загрузкой.
     *
     * @return Сервис упаковки с равномерной загрузкой.
     */
    PackagingService createSinglePackagingService();
}
