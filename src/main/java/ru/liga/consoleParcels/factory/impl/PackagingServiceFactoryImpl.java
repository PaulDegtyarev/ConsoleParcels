package ru.liga.consoleParcels.factory.impl;

import org.springframework.stereotype.Component;
import ru.liga.consoleParcels.factory.PackagingServiceFactory;
import ru.liga.consoleParcels.service.PackagingService;
import ru.liga.consoleParcels.service.impl.OptimizedPackagingServiceImpl;
import ru.liga.consoleParcels.service.impl.BalancedLoadingServiceImpl;

/**
 * Реализация фабрики для создания сервисов упаковки.
 * <p>
 * Эта фабрика создает конкретные реализации сервисов упаковки,
 * таких как {@link OptimizedPackagingServiceImpl} и
 * {@link BalancedLoadingServiceImpl}, используя фабрику для
 * создания грузовиков {@link DefaultTruckFactory}.
 *
 * @see PackagingServiceFactory
 */
@Component
public class PackagingServiceFactoryImpl implements PackagingServiceFactory {
    /**
     * Создает сервис для оптимизированной упаковки.
     * <p>
     * Сервис создается с использованием фабрики для создания
     * грузовиков {@link DefaultTruckFactory}.
     *
     * @return Сервис оптимизированной упаковки.
     */
    @Override
    public PackagingService createOptimizedPackagingService() {
        return new OptimizedPackagingServiceImpl(
                new DefaultTruckFactory()
        );
    }

    /**
     * Создает сервис для упаковки с равномерной загрузкой.
     * <p>
     * Сервис создается с использованием фабрики для создания
     * грузовиков {@link DefaultTruckFactory}.
     *
     * @return Сервис упаковки с равномерной загрузкой.
     */
    @Override
    public PackagingService createSinglePackagingService() {
        return new BalancedLoadingServiceImpl(
                new DefaultTruckFactory()
        );
    }
}
