package ConsoleParcelsApp.service;

import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.impl.OptimizedPackagingServiceImpl;
import ConsoleParcelsApp.util.PackageReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class OptimizedPackagingServiceImplTest {
    private OptimizedPackagingServiceImpl service;
    private String filePath = "src/test/resources/input/valid-input-data.txt";

    @BeforeEach
    public void setUp() {
        service = new OptimizedPackagingServiceImpl(new PackageReader());
    }

    @Test
    public void packPackages_withCorrectInput_shouldReturnCorrectOutput() throws IOException {
        List<Truck> expectedResult = service.packPackages(filePath, 2);

        assertThat(expectedResult.size()).isEqualTo(1);
    }
}
