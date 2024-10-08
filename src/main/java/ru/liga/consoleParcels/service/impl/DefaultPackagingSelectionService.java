package ru.liga.consoleParcels.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.factory.PackagingServiceFactory;
import ru.liga.consoleParcels.model.TruckPackageAlgorithm;
import ru.liga.consoleParcels.service.PackagingSelectionService;
import ru.liga.consoleParcels.service.PackagingService;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация сервиса для выбора алгоритма упаковки посылок.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultPackagingSelectionService implements PackagingSelectionService {
    private final PackagingServiceFactory packagingServiceFactory;
    private Map<TruckPackageAlgorithm, PackagingService> serviceMap = new HashMap<>();

    private void initializeServiceMap() {
        serviceMap.put(TruckPackageAlgorithm.MAX_SPACE, packagingServiceFactory.createOptimizedPackagingService());
        serviceMap.put(TruckPackageAlgorithm.EVEN_LOADING, packagingServiceFactory.createSinglePackagingService());
    }

    /**
     * Выбирает сервис упаковки на основе выбранного алгоритма.
     *
     * @param algorithmChoice Выбранный алгоритм упаковки.
     * @return Сервис упаковки, соответствующий выбранному алгоритму.
     */
    @Override
    public PackagingService selectPackagingService(TruckPackageAlgorithm algorithmChoice) {
        log.debug("Начинается выбор сервис для упаковки по номеру алгоритма: {}", algorithmChoice);

        return serviceMap.get(algorithmChoice);
    }
}
