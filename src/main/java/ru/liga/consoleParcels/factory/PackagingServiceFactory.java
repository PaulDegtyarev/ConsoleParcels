package ru.liga.consoleParcels.factory;

import ru.liga.consoleParcels.service.PackagingService;


public interface PackagingServiceFactory {
    PackagingService createOptimizedPackagingService();

    PackagingService createSinglePackagingService();
}
