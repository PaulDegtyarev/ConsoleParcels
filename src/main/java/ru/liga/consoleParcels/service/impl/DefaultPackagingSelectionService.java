package ru.liga.consoleParcels.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.factory.PackagingServiceFactory;
import ru.liga.consoleParcels.service.PackagingSelectionService;
import ru.liga.consoleParcels.service.PackagingService;
import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация сервиса для выбора сервиса упаковки.
 * <p>
 * Этот сервис хранит карту, связывающую варианты выбора
 * алгоритма пользователя с конкретными сервисами упаковки.
 * В зависимости от варианта выбора пользователя, он
 * возвращает соответствующий сервис упаковки.
 *
 * @see PackagingSelectionService
 */
@Log4j2
@Service
public class DefaultPackagingSelectionService implements PackagingSelectionService {
    private PackagingServiceFactory packagingServiceFactory;
    private Map<UserAlgorithmChoice, PackagingService> serviceMap = new HashMap<>();

    /**
     * Конструктор сервиса выбора сервиса упаковки.
     * <p>
     * Инициализирует карту сервисов упаковки.
     *
     * @param packagingServiceFactory Фабрика для создания
     *                                сервисов упаковки.
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
     * Выбирает сервис упаковки в зависимости от выбранного
     * пользователем алгоритма.
     *
     * @param algorithmChoice Выбранный пользователем алгоритм
     *                        упаковки.
     * @return Сервис упаковки, соответствующий выбранному
     * алгоритму.
     */
    @Override
    public PackagingService selectPackagingService(UserAlgorithmChoice algorithmChoice) {
        log.debug("Начинается выбор сервис для упаковки по номеру алгоритма: {}", algorithmChoice);

        return serviceMap.get(algorithmChoice);
    }
}
