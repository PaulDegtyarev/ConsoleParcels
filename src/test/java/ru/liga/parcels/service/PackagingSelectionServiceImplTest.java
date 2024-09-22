package ru.liga.parcels.service;

import ru.liga.parcels.factory.PackagingServiceFactory;
import ru.liga.parcels.factory.impl.PackagingServiceFactoryImpl;
import ru.liga.parcels.service.impl.OptimizedPackagingServiceImpl;
import ru.liga.parcels.service.impl.PackagingSelectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.parcels.util.UserAlgorithmChoice;

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
        UserAlgorithmChoice algorithmChoice = UserAlgorithmChoice.MAX_SPACE;
        PackagingService actualResponse = packagingSelectionService.selectPackagingService(algorithmChoice);

        assertThat(actualResponse).isInstanceOf(OptimizedPackagingServiceImpl.class);
    }
}
