package ConsoleParcelsApp.service;

import ConsoleParcelsApp.factory.PackagingServiceFactory;
import ConsoleParcelsApp.factory.impl.PackagingServiceFactoryImpl;
import ConsoleParcelsApp.service.impl.OptimizedPackagingServiceImpl;
import ConsoleParcelsApp.service.impl.PackagingSelectionServiceImpl;
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
