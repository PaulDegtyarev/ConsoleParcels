package ConsoleParcelsApp.service;

import ConsoleParcelsApp.service.impl.SinglePackagingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SinglePackagingServiceImplTest {
    private SinglePackagingServiceImpl service;
    private String filePath = "src/test/resources/input/valid-input-data.txt";

    @BeforeEach
    public void setUp() {
        service = new SinglePackagingServiceImpl();
    }

    @Test
    public void packPackagesSuccess() throws IOException {
        service.packPackages(filePath);

        assertEquals(6, service.getTrucks().size());
    }
}
