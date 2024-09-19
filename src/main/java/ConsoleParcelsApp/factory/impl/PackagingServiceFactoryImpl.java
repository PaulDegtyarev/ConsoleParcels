package ConsoleParcelsApp.factory.impl;

import ConsoleParcelsApp.factory.PackagingServiceFactory;
import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.service.impl.OptimizedPackagingServiceImpl;
import ConsoleParcelsApp.service.impl.BalancedLoadingServiceImpl;
import ConsoleParcelsApp.util.PackageReader;

public class PackagingServiceFactoryImpl implements PackagingServiceFactory {
    @Override
    public PackagingService createOptimizedPackagingService() {
        return new OptimizedPackagingServiceImpl(
                new PackageReader(),
                new TruckFactoryImpl()
        );
    }

    @Override
    public PackagingService createSinglePackagingService() {
        return new BalancedLoadingServiceImpl(
                new PackageReader(),
                new TruckFactoryImpl()
        );
    }
}
