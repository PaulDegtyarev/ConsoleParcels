package ConsoleParcelsApp.service;

import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.impl.BalancedLoadingServiceImpl;
import ConsoleParcelsApp.util.PackageReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BalancedLoadingServiceImplTest {
    private BalancedLoadingServiceImpl service;
    private String filePath = "src/test/resources/input/valid-input-data-for-balanced-loading-service.txt";

    @BeforeEach
    public void setUp() {
        service = new BalancedLoadingServiceImpl(new PackageReader());
    }

    @Test
    public void packPackages_withCorrectInput_shouldReturnCorrectOutput() throws IOException {
        List<Truck> expectedResult = service.packPackages(filePath,2 );

        assertThat(expectedResult.size()).isEqualTo(2);
    }

    @Test
    void packPackage_withDataThatDoesNotFitInOneTruck_shouldThrowRunTimeException() {
        filePath = "src/test/resources/input/input-data-does-not-fit-in-one-truck.txt";

        assertThrows(RuntimeException.class, () -> service.packPackages(filePath, 1));
    }
}
