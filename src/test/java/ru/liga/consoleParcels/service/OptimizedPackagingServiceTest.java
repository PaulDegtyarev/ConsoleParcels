package ru.liga.consoleParcels.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.impl.OptimizedPackagingService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OptimizedPackagingServiceTest {
    @InjectMocks
    private OptimizedPackagingService service;

    @Mock
    private TruckFactory truckFactory;

    @Mock
    private ParcelCountingService parcelCountingService;

    @Mock
    private ParcelQuantityRecordingService parcelQuantityRecordingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new OptimizedPackagingService(truckFactory, parcelCountingService, parcelQuantityRecordingService);
    }

    @Test
    void packPackages_withDataThatDoesNotFitInOneTruck_shouldThrowPackingException() {
        List<ParcelForPackagingDto> parcels = new ArrayList<>(List.of(
                new ParcelForPackagingDto(3, 3, new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}}),
                new ParcelForPackagingDto(3, 3, new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}})
        ));

        assertThrows(PackingException.class, () -> service.packPackages(parcels, "1x1"));
    }

    @Test
    void packPackages_withMultipleTrucks_shouldDistributeParcelsCorrectly() {
        // Arrange
        List<ParcelForPackagingDto> parcels = List.of(
                new ParcelForPackagingDto(2, 2, new char[][]{{'A', 'A'}, {'A', 'A'}}),
                new ParcelForPackagingDto(2, 2, new char[][]{{'B', 'B'}, {'B', 'B'}})
        );

        // Act
        List<Truck> result = service.packPackages(parcels, "2x2, 2x2");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

}
