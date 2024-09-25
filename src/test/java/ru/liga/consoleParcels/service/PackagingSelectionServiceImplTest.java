package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.factory.PackagingServiceFactory;
import ru.liga.consoleParcels.factory.impl.PackagingServiceFactoryImpl;
import ru.liga.consoleParcels.service.impl.OptimizedPackagingServiceImpl;
import ru.liga.consoleParcels.service.impl.PackagingSelectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;

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
