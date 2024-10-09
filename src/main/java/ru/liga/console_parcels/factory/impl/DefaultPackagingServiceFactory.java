package ru.liga.console_parcels.factory.impl;

import org.springframework.stereotype.Component;
import ru.liga.console_parcels.factory.PackagingServiceFactory;
import ru.liga.console_parcels.service.PackagingService;
import ru.liga.console_parcels.service.impl.BalancedLoadingService;
import ru.liga.console_parcels.service.impl.DefaultParcelCountingService;
import ru.liga.console_parcels.service.impl.OptimizedPackagingService;
import ru.liga.console_parcels.service.impl.ServiceForRecordingNumberOfParcelsToJsonFile;

/**
 * Фабрика сервисов упаковки по умолчанию.
 *
 * <p>
 * Этот класс реализует интерфейс {@link PackagingServiceFactory} и предоставляет методы для создания различных сервисов упаковки.
 * Он отвечает за создание оптимизированного сервиса упаковки и сервиса одиночной упаковки с использованием конкретных реализаций фабрик и сервисов.
 * </p>
 */
@Component
public class DefaultPackagingServiceFactory implements PackagingServiceFactory {
    /**
     * Метод для создания оптимизированного сервиса упаковки.
     *
     * @return Экземпляр {@link PackagingService}, представляющий оптимизированный сервис упаковки.
     */
    @Override
    public PackagingService createOptimizedPackagingService() {
        return new OptimizedPackagingService(
                new DefaultTruckFactory(),
                new DefaultParcelCountingService(),
                new ServiceForRecordingNumberOfParcelsToJsonFile()
        );
    }

    /**
     * Метод для создания сервиса одиночной упаковки.
     *
     * @return Экземпляр {@link PackagingService}, представляющий сервис одиночной упаковки.
     */
    @Override
    public PackagingService createSinglePackagingService() {
        return new BalancedLoadingService(
                new DefaultTruckFactory(),
                new DefaultParcelCountingService(),
                new ServiceForRecordingNumberOfParcelsToJsonFile()
        );
    }
}
