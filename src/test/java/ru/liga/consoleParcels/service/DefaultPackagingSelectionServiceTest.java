package ru.liga.consoleParcels.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.consoleParcels.factory.PackagingServiceFactory;
import ru.liga.consoleParcels.factory.impl.DefaultPackagingServiceFactory;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;
import ru.liga.consoleParcels.service.impl.DefaultPackagingSelectionService;
import ru.liga.consoleParcels.service.impl.OptimizedPackagingService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DefaultPackagingSelectionServiceTest {
    private PackagingServiceFactory packagingServiceFactory;
    private PackagingSelectionService packagingSelectionService;

    @BeforeEach
    void setUp() {
        packagingServiceFactory = new DefaultPackagingServiceFactory();
        packagingSelectionService = new DefaultPackagingSelectionService(packagingServiceFactory);
    }

    @Test
    void selectPackagingService_withValidInput_shouldReturnValidOutput() {
        UserAlgorithmChoice algorithmChoice = UserAlgorithmChoice.MAX_SPACE;
        PackagingService actualResponse = packagingSelectionService.selectPackagingService(algorithmChoice);

        assertThat(actualResponse).isInstanceOf(OptimizedPackagingService.class);
    }
}
