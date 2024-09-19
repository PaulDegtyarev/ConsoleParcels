package ConsoleParcelsApp.service;

import ConsoleParcelsApp.factory.impl.TruckFactoryImpl;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.impl.OptimizedPackagingServiceImpl;
import ConsoleParcelsApp.util.PackageReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OptimizedPackagingServiceImplTest {
    private OptimizedPackagingServiceImpl service;
    private String filePath = "src/test/resources/input/valid-input-data-for-optimized-loading-service.txt";

    @BeforeEach
    public void setUp() {
        service = new OptimizedPackagingServiceImpl(
                new PackageReader(),
                new TruckFactoryImpl());
    }

    @Test
    void packPackages_withCorrectInput_shouldReturnCorrectOutput() throws IOException {
        List<Truck> expectedResult = service.packPackages(filePath, 2);

        assertThat(expectedResult.size()).isEqualTo(2);
    }

    @Test
    void packPackage_withDataThatDoesNotFitInOneTruck_shouldThrowRunTimeException() {
        filePath = "src/test/resources/input/input-data-does-not-fit-in-one-truck.txt";

        assertThrows(RuntimeException.class, () -> service.packPackages(filePath, 1));
    }
}
