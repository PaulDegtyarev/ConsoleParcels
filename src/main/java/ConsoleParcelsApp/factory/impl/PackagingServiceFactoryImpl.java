package ConsoleParcelsApp.factory.impl;

import ConsoleParcelsApp.factory.PackagingServiceFactory;
import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.service.impl.OptimizedPackagingServiceImpl;
import ConsoleParcelsApp.service.impl.SinglePackagingServiceImpl;
import ConsoleParcelsApp.util.PackageReader;

public class PackagingServiceFactoryImpl implements PackagingServiceFactory {
    @Override
    public PackagingService createOptimizedPackagingService() {
        return new OptimizedPackagingServiceImpl(new PackageReader());
    }

    @Override
    public PackagingService createSinglePackagingService() {
        return new SinglePackagingServiceImpl(new PackageReader());
    }
}
