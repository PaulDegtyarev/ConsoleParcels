package ru.liga.consoleParcels.factory.impl;

import ru.liga.consoleParcels.factory.PackagingServiceFactory;
import ru.liga.consoleParcels.service.PackagingService;
import ru.liga.consoleParcels.service.impl.OptimizedPackagingServiceImpl;
import ru.liga.consoleParcels.service.impl.BalancedLoadingServiceImpl;

public class PackagingServiceFactoryImpl implements PackagingServiceFactory {
    @Override
    public PackagingService createOptimizedPackagingService() {
        return new OptimizedPackagingServiceImpl(
                new TruckFactoryImpl()
        );
    }

    @Override
    public PackagingService createSinglePackagingService() {
        return new BalancedLoadingServiceImpl(
                new TruckFactoryImpl()
        );
    }
}
