package ru.liga.parcels.factory;

import ru.liga.parcels.service.PackagingService;

public interface PackagingServiceFactory {
    PackagingService createOptimizedPackagingService();

    PackagingService createSinglePackagingService();
}
