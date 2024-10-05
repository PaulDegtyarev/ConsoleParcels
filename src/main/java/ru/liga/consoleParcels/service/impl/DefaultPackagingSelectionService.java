package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.factory.PackagingServiceFactory;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;
import ru.liga.consoleParcels.service.PackagingSelectionService;
import ru.liga.consoleParcels.service.PackagingService;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация сервиса для выбора алгоритма упаковки посылок.
 */
@Log4j2
@Service
public class DefaultPackagingSelectionService implements PackagingSelectionService {
    private PackagingServiceFactory packagingServiceFactory;
    private Map<UserAlgorithmChoice, PackagingService> serviceMap = new HashMap<>();

    /**
     * Конструктор с зависимостью от фабрики сервисов упаковки.
     *
     * @param packagingServiceFactory Фабрика сервисов упаковки.
     */
    @Autowired
    public DefaultPackagingSelectionService(PackagingServiceFactory packagingServiceFactory) {
        this.packagingServiceFactory = packagingServiceFactory;
        initializeServiceMap();
    }

    private void initializeServiceMap() {
        serviceMap.put(UserAlgorithmChoice.MAX_SPACE, packagingServiceFactory.createOptimizedPackagingService());
        serviceMap.put(UserAlgorithmChoice.EVEN_LOADING, packagingServiceFactory.createSinglePackagingService());
    }

    /**
     * Выбирает сервис упаковки на основе выбранного алгоритма.
     *
     * @param algorithmChoice Выбранный алгоритм упаковки.
     * @return Сервис упаковки, соответствующий выбранному алгоритму.
     */
    @Override
    public PackagingService selectPackagingService(UserAlgorithmChoice algorithmChoice) {
        log.debug("Начинается выбор сервис для упаковки по номеру алгоритма: {}", algorithmChoice);

        return serviceMap.get(algorithmChoice);
    }
}
