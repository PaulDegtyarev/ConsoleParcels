package ConsoleParcelsApp.factory;

import ConsoleParcelsApp.service.PackagingService;

public interface PackagingServiceFactory {
    PackagingService createOptimizedPackagingService();

    PackagingService createSinglePackagingService();
}
