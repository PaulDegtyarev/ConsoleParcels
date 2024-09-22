package ru.liga.parcels.service.impl;

import ru.liga.parcels.factory.PackagingServiceFactory;
import ru.liga.parcels.service.PackagingSelectionService;
import ru.liga.parcels.service.PackagingService;
import lombok.extern.log4j.Log4j2;
import ru.liga.parcels.util.UserAlgorithmChoice;

@Log4j2
public class PackagingSelectionServiceImpl implements PackagingSelectionService {
    private PackagingServiceFactory packagingServiceFactory;

    public PackagingSelectionServiceImpl(PackagingServiceFactory packagingServiceFactory) {
        this.packagingServiceFactory = packagingServiceFactory;
    }

    @Override
    public PackagingService selectPackagingService(UserAlgorithmChoice algorithmChoice) {
        log.debug("Начинается выбор сервис для упаковки по номеру алгоритма: {}", algorithmChoice);

        return switch (algorithmChoice) {
            case MAX_SPACE -> {
                log.info("Создан OptimizedPackagingService");
                yield packagingServiceFactory.createOptimizedPackagingService();

            }
            case EVEN_LOADING -> {
                log.info("Создан SinglePackagingService");
                yield packagingServiceFactory.createSinglePackagingService();
            }
        };
    }
}
