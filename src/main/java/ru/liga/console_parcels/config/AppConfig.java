package ru.liga.console_parcels.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.liga.console_parcels.dto.TruckPackageAlgorithm;
import ru.liga.console_parcels.service.TruckPackageService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {
    @Bean
    public Map<TruckPackageAlgorithm, TruckPackageService> packagingServiceMap(
            @Qualifier("EVEN_LOADING") TruckPackageService evenLoadingService,
            @Qualifier("MAX_SPACE") TruckPackageService maxSpaceService) {
        Map<TruckPackageAlgorithm, TruckPackageService> serviceMap = new HashMap<>();
        serviceMap.put(TruckPackageAlgorithm.EVEN_LOADING, evenLoadingService);
        serviceMap.put(TruckPackageAlgorithm.MAX_SPACE, maxSpaceService);
        return serviceMap;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
