package ru.liga.consoleParcels.service.impl;

import ru.liga.consoleParcels.factory.PackagingServiceFactory;
import ru.liga.consoleParcels.service.PackagingSelectionService;
import ru.liga.consoleParcels.service.PackagingService;
import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;

import java.util.Map;

@Log4j2
public class PackagingSelectionServiceImpl implements PackagingSelectionService {
    private PackagingServiceFactory packagingServiceFactory;
    private final Map<UserAlgorithmChoice, PackagingService> serviceMap = initializeServiceMap();

    public PackagingSelectionServiceImpl(PackagingServiceFactory packagingServiceFactory) {
        this.packagingServiceFactory = packagingServiceFactory;
    }

    private Map<UserAlgorithmChoice, PackagingService> initializeServiceMap() {
        serviceMap.put(UserAlgorithmChoice.MAX_SPACE, packagingServiceFactory.createOptimizedPackagingService());
        serviceMap.put(UserAlgorithmChoice.EVEN_LOADING, packagingServiceFactory.createSinglePackagingService());

        return serviceMap;
    }

    @Override
    public PackagingService selectPackagingService(UserAlgorithmChoice algorithmChoice) {
        log.debug("Начинается выбор сервис для упаковки по номеру алгоритма: {}", algorithmChoice);

        return serviceMap.get(algorithmChoice);
    }
}
