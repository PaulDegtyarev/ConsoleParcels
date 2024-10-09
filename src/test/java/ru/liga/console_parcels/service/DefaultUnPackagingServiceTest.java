package ru.liga.console_parcels.service;

import org.junit.jupiter.api.Test;
import ru.liga.console_parcels.dto.UnPackedTruckDto;
import ru.liga.console_parcels.exception.FileReadException;
import ru.liga.console_parcels.service.impl.DefaultUnPackagingService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class DefaultUnPackagingServiceTest {
    private UnPackagingService unPackagingService = new DefaultUnPackagingService();

    @Test
    void unpackTruck_withValidInput_shouldReturnValidOutput() {
        String truckFilePath = "src/test/resources/input/valid-input-trucks.json";

        List<UnPackedTruckDto> result = unPackagingService.unpackTruck(truckFilePath);

        assertThat(result.size()).isEqualTo(1);

        UnPackedTruckDto unPackedTruck = result.get(0);
        assertThat(unPackedTruck.getTruckId()).isEqualTo(1);

        List<List<String>> packageLayout = unPackedTruck.getPackageLayout();
        assertThat(packageLayout.size()).isEqualTo(6);
    }

    @Test
    void unpackTruck_withInvalidTruckFilePath_shouldThrowFileReadException() {
        String invalidTruckFilePath = "invalid_truck_file.json";
        String parcelCountFilePath = "src/test/resources/input/parcel-count-to-test-unpacking.json";

        assertThatThrownBy(() -> unPackagingService.unpackTruck(invalidTruckFilePath))
                .isInstanceOf(FileReadException.class)
                .hasMessageContaining("Ошибка при чтении JSON файла: " + invalidTruckFilePath);
    }
}
