package ru.liga.consoleParcels.factory.impl;

import ru.liga.consoleParcels.factory.PackagingServiceFactory;
import ru.liga.consoleParcels.service.PackagingService;
import ru.liga.consoleParcels.service.impl.OptimizedPackagingServiceImpl;
import ru.liga.consoleParcels.service.impl.BalancedLoadingServiceImpl;

/**
 * Реализация фабрики для создания сервисов упаковки.
 *
 * Эта фабрика создает конкретные реализации сервисов упаковки,
 * таких как {@link OptimizedPackagingServiceImpl} и
 * {@link BalancedLoadingServiceImpl}, используя фабрику для
 * создания грузовиков {@link TruckFactoryImpl}.
 *
 * @see PackagingServiceFactory
 */
public class PackagingServiceFactoryImpl implements PackagingServiceFactory {
    /**
     * Создает сервис для оптимизированной упаковки.
     *
     * Сервис создается с использованием фабрики для создания
     * грузовиков {@link TruckFactoryImpl}.
     *
     * @return Сервис оптимизированной упаковки.
     */
    @Override
    public PackagingService createOptimizedPackagingService() {
        return new OptimizedPackagingServiceImpl(
                new TruckFactoryImpl()
        );
    }

    /**
     * Создает сервис для упаковки с равномерной загрузкой.
     *
     * Сервис создается с использованием фабрики для создания
     * грузовиков {@link TruckFactoryImpl}.
     *
     * @return Сервис упаковки с равномерной загрузкой.
     */
    @Override
    public PackagingService createSinglePackagingService() {
        return new BalancedLoadingServiceImpl(
                new TruckFactoryImpl()
        );
    }
}
