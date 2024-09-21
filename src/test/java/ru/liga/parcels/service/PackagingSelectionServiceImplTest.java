package ru.liga.parcels.service;

import ru.liga.parcels.factory.PackagingServiceFactory;
import ru.liga.parcels.factory.impl.PackagingServiceFactoryImpl;
import ru.liga.parcels.service.PackagingSelectionService;
import ru.liga.parcels.service.PackagingService;
import ru.liga.parcels.service.impl.OptimizedPackagingServiceImpl;
import ru.liga.parcels.service.impl.PackagingSelectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PackagingSelectionServiceImplTest {
    private PackagingServiceFactory packagingServiceFactory;
    private PackagingSelectionService packagingSelectionService;

    @BeforeEach
    void setUp() {
        packagingServiceFactory = new PackagingServiceFactoryImpl();
        packagingSelectionService = new PackagingSelectionServiceImpl(packagingServiceFactory);
    }

    @Test
    void selectPackagingService_withValidInput_shouldReturnValidOutput() {
        PackagingService actualResponse = packagingSelectionService.selectPackagingService(1);

        assertThat(actualResponse).isInstanceOf(OptimizedPackagingServiceImpl.class);
    }
}
