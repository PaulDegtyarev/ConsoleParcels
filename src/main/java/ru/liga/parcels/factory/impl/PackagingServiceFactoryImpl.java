package ru.liga.parcels.factory.impl;

import ru.liga.parcels.factory.PackagingServiceFactory;
import ru.liga.parcels.service.PackagingService;
import ru.liga.parcels.service.impl.OptimizedPackagingServiceImpl;
import ru.liga.parcels.service.impl.BalancedLoadingServiceImpl;

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
