package ru.liga.console_parcels.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.TruckPackageAlgorithm;
import ru.liga.console_parcels.service.PackagingSelectionService;
import ru.liga.console_parcels.service.TruckPackageService;

import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultPackagingSelectionService implements PackagingSelectionService {
    @Autowired
    private final Map<TruckPackageAlgorithm, TruckPackageService> serviceMap;

    @Override
    public TruckPackageService selectPackagingService(TruckPackageAlgorithm algorithmChoice) {
        log.debug("Начинается выбор сервис для упаковки названию алгоритма: {}", algorithmChoice);

        return serviceMap.get(algorithmChoice);
    }
}
