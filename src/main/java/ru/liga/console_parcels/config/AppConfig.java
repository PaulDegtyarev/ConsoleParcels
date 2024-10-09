package ru.liga.console_parcels.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.liga.console_parcels.model.UserAlgorithmChoice;
import ru.liga.console_parcels.service.TruckPackageService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {
    @Bean
    public Map<UserAlgorithmChoice, TruckPackageService> packagingServiceMap(
            @Qualifier("EVEN_LOADING") TruckPackageService evenLoadingService,
            @Qualifier("MAX_SPACE") TruckPackageService maxSpaceService) {
        Map<UserAlgorithmChoice, TruckPackageService> serviceMap = new HashMap<>();
        serviceMap.put(UserAlgorithmChoice.EVEN_LOADING, evenLoadingService);
        serviceMap.put(UserAlgorithmChoice.MAX_SPACE, maxSpaceService);
        return serviceMap;
    }
}
