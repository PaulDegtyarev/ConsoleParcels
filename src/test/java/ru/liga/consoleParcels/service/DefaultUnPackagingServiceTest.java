package ru.liga.consoleParcels.service;

import org.junit.jupiter.api.Test;
import ru.liga.consoleParcels.dto.UnpackedTruckDto;
import ru.liga.consoleParcels.exception.FileReadException;
import ru.liga.consoleParcels.service.impl.DefaultUnPackagingService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class DefaultUnPackagingServiceTest {
    private UnPackagingService unPackagingService = new DefaultUnPackagingService();

    @Test
    void unpackTruck_withValidInput_shouldReturnValidOutput() {
        String truckFilePath = "data/trucks.json";
        String parcelCountFilePath = "data/trucks-with-number-of-parcels.json";

        List<UnpackedTruckDto> result = unPackagingService.unpackTruck(truckFilePath);

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

        assertThatThrownBy(() -> unPackagingService.unpackTruck(invalidTruckFilePath))
                .isInstanceOf(FileReadException.class)
                .hasMessageContaining("Ошибка при чтении JSON файла: " + invalidTruckFilePath);
    }

    @Test
    void unpackTruck_withInvalidParcelCountFilePath_shouldThrowFileReadException() {
        String truckFilePath = "src/test/resources/input/truck-to-test-unpacking.json";
        String invalidParcelCountFilePath = "invalid_parcel_count_file.json";

        assertThatThrownBy(() -> unPackagingService.unpackTruck(truckFilePath))
                .isInstanceOf(FileReadException.class)
                .hasMessageContaining("Ошибка при чтении JSON файла: " + invalidParcelCountFilePath);
    }
}
