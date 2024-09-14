package ConsoleParcelsApp.service;

import ConsoleParcelsApp.factory.PackagingServiceFactory;
import ConsoleParcelsApp.service.impl.OptimizedPackagingServiceImpl;
import ConsoleParcelsApp.service.impl.PackagingSelectionServiceImpl;
import ConsoleParcelsApp.util.PackageReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class PackagingSelectionServiceImplTest {
    private PackagingServiceFactory packagingServiceFactory;
    private PackagingSelectionService packagingSelectionService;

    @BeforeEach
    void setUp() {
        packagingServiceFactory = Mockito.mock(PackagingServiceFactory.class);
        packagingSelectionService = new PackagingSelectionServiceImpl(packagingServiceFactory);
    }

    @Test
    void selectPackagingService_withValidInput_shouldReturnValidOutput() {
        when(packagingServiceFactory.createOptimizedPackagingService()).thenReturn(new OptimizedPackagingServiceImpl(new PackageReader()));

        PackagingService actualResponse = packagingSelectionService.selectPackagingService(1);

        assertThat(actualResponse).isInstanceOf(OptimizedPackagingServiceImpl.class);
    }
}
