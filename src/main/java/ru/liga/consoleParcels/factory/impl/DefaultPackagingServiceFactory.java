package ru.liga.consoleParcels.factory.impl;

import org.springframework.stereotype.Component;
import ru.liga.consoleParcels.factory.PackagingServiceFactory;
import ru.liga.consoleParcels.service.PackagingService;
import ru.liga.consoleParcels.service.impl.DefaultParcelCountingService;
import ru.liga.consoleParcels.service.impl.OptimizedPackagingService;
import ru.liga.consoleParcels.service.impl.BalancedLoadingService;
import ru.liga.consoleParcels.service.impl.ServiceForRecordingNumberOfParcelsToJsonFile;

@Component
public class DefaultPackagingServiceFactory implements PackagingServiceFactory {
    @Override
    public PackagingService createOptimizedPackagingService() {
        return new OptimizedPackagingService(
                new DefaultTruckFactory(),
                new DefaultParcelCountingService(),
                new ServiceForRecordingNumberOfParcelsToJsonFile()
        );
    }

    @Override
    public PackagingService createSinglePackagingService() {
        return new BalancedLoadingService(
                new DefaultTruckFactory(),
                new DefaultParcelCountingService(),
                new ServiceForRecordingNumberOfParcelsToJsonFile()
        );
    }
}
