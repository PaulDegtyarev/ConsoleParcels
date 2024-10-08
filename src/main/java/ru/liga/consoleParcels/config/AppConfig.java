package ru.liga.consoleParcels.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.liga.consoleParcels.model.TruckPackageAlgorithm;
import ru.liga.consoleParcels.service.PackagingService;
import ru.liga.consoleParcels.service.impl.BalancedLoadingService;
import ru.liga.consoleParcels.service.impl.OptimizedPackagingService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {
    @Autowired
    private BalancedLoadingService balancedLoadingService;
    @Autowired
    private OptimizedPackagingService optimizedPackagingService;

    @Bean
    public Map<TruckPackageAlgorithm, PackagingService> packagingServiceMap() {
        Map<TruckPackageAlgorithm, PackagingService> serviceMap = new HashMap<>();
        serviceMap.put(TruckPackageAlgorithm.MAX_SPACE, balancedLoadingService);
        serviceMap.put(TruckPackageAlgorithm.EVEN_LOADING, optimizedPackagingService);
        return serviceMap;
    }
}
