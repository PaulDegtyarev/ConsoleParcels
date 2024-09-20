package ConsoleParcelsApp.service;

import ConsoleParcelsApp.dto.UnPackedTruckDto;
import ConsoleParcelsApp.exception.FileReadException;
import ConsoleParcelsApp.factory.DelimeterFactory;
import ConsoleParcelsApp.factory.impl.DelimeterFactoryImpl;
import ConsoleParcelsApp.service.impl.UnPackagingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnPackagingServiceImplTest {
    private UnPackagingService unPackagingService;
    private DelimeterFactory delimeterFactory;

    @BeforeEach
    void setUp() {
        delimeterFactory = new DelimeterFactoryImpl();
        unPackagingService = new UnPackagingServiceImpl(delimeterFactory);
    }

    @Test
    void unpackTruck_withValidInput_shouldReturnValidOutput() {
        String filePath = "src/test/resources/input/truck-to-test-unpacking.json";

        List<UnPackedTruckDto> result = unPackagingService.unpackTruck(filePath);

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void unpackTruck_fileNotFound() {
        String filePath = "non_existent_file.json";
        assertThrows(FileReadException.class, () -> unPackagingService.unpackTruck(filePath));
    }
}
