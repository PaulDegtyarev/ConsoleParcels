package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.factory.PackagingServiceFactory;
import ConsoleParcelsApp.service.PackagingSelectionService;
import ConsoleParcelsApp.service.PackagingService;

public class PackagingSelectionServiceImpl implements PackagingSelectionService {
    private PackagingServiceFactory packagingServiceFactory;

    public PackagingSelectionServiceImpl(PackagingServiceFactory packagingServiceFactory) {
        this.packagingServiceFactory = packagingServiceFactory;
    }

    @Override
    public PackagingService selectPackagingService(int algorithmChoice) {
        return switch (algorithmChoice) {
            case 1 -> packagingServiceFactory.createOptimizedPackagingService();
            case 2 -> packagingServiceFactory.createSinglePackagingService();
            default -> null;
        };
    }
}
