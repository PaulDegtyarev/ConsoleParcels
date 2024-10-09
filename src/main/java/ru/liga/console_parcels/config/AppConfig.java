package ru.liga.console_parcels.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.liga.console_parcels.dto.TruckPackageAlgorithm;
import ru.liga.console_parcels.service.TruckPackageService;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурационный класс приложения.
 * Содержит определения бинов для использования в контексте Spring.
 */
@Configuration
public class AppConfig {
    /**
     * Создает и настраивает мапу, связывающую алгоритмы упаковки грузовиков с их соответствующими сервисами.
     *
     * @param evenLoadingService сервис, реализующий алгоритм равномерной загрузки
     * @param maxSpaceService    сервис, реализующий алгоритм максимального использования пространства
     * @return мапа, сопоставляющая алгоритмы упаковки с соответствующими сервисами
     */
    @Bean
    public Map<TruckPackageAlgorithm, TruckPackageService> packagingServiceMap(
            @Qualifier("EVEN_LOADING") TruckPackageService evenLoadingService,
            @Qualifier("MAX_SPACE") TruckPackageService maxSpaceService) {
        Map<TruckPackageAlgorithm, TruckPackageService> serviceMap = new HashMap<>();
        serviceMap.put(TruckPackageAlgorithm.EVEN_LOADING, evenLoadingService);
        serviceMap.put(TruckPackageAlgorithm.MAX_SPACE, maxSpaceService);
        return serviceMap;
    }

    /**
     * Создает и настраивает экземпляр ObjectMapper для использования в приложении.
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}