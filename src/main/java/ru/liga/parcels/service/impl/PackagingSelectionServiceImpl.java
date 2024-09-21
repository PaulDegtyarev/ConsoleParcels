package ru.liga.parcels.service.impl;

import ru.liga.parcels.factory.PackagingServiceFactory;
import ru.liga.parcels.service.PackagingSelectionService;
import ru.liga.parcels.service.PackagingService;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PackagingSelectionServiceImpl implements PackagingSelectionService {
    private PackagingServiceFactory packagingServiceFactory;

    public PackagingSelectionServiceImpl(PackagingServiceFactory packagingServiceFactory) {
        this.packagingServiceFactory = packagingServiceFactory;
    }

    @Override
    public PackagingService selectPackagingService(int algorithmChoice) {
        log.debug("Начинается выбор сервис для упаковки по номеру алгоритма: {}", algorithmChoice);

        return switch (algorithmChoice) {
            case 1 -> {
                log.info("Создан OptimizedPackagingService");
                yield packagingServiceFactory.createOptimizedPackagingService();

            }
            case 2 -> {
                log.info("Создан SinglePackagingService");
                yield packagingServiceFactory.createSinglePackagingService();
            }
            default -> throw new IllegalStateException("Нет такого номера алгоритма " + algorithmChoice);
        };
    }
}
