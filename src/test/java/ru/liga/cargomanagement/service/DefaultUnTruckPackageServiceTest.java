package ru.liga.cargomanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.liga.cargomanagement.dto.UnpackedTruckDto;
import ru.liga.cargomanagement.exception.FileReadException;
import ru.liga.cargomanagement.service.impl.JsonFileUnpackingService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class DefaultUnTruckPackageServiceTest {
    private FileUnpackingService fileUnpackakingService = new JsonFileUnpackingService(
            new ObjectMapper()
    );

    @Test
    void unpackTruck_withValidInput_shouldReturnValidOutput() {
        String truckFilePath = "src/test/resources/input/valid-input-trucks.json";

        List<UnpackedTruckDto> result = fileUnpackakingService.unpackTruck(truckFilePath);

        assertThat(result.size()).isEqualTo(1);

        UnpackedTruckDto unPackedTruck = result.get(0);
        assertThat(unPackedTruck.getTruckId()).isEqualTo(1);

        List<List<String>> packageLayout = unPackedTruck.getPackageLayout();
        assertThat(packageLayout.size()).isEqualTo(6);
    }

    @Test
    void unpackTruck_withInvalidTruckFilePath_shouldThrowFileReadException() {
        String invalidTruckFilePath = "invalid_truck_file.json";
        String parcelCountFilePath = "src/test/resources/input/parcel-count-to-test-unpacking.json";

        assertThatThrownBy(() -> fileUnpackakingService.unpackTruck(invalidTruckFilePath))
                .isInstanceOf(FileReadException.class)
                .hasMessageContaining("Ошибка при чтении JSON файла: " + invalidTruckFilePath);
    }
}
