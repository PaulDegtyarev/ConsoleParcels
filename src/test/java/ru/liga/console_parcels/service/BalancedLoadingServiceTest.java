package ru.liga.console_parcels.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.liga.console_parcels.dto.ParcelForPackagingDto;
import ru.liga.console_parcels.exception.PackingException;
import ru.liga.console_parcels.factory.TruckFactory;
import ru.liga.console_parcels.model.Truck;
import ru.liga.console_parcels.service.impl.BalancedLoadingService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BalancedLoadingServiceTest {

    private BalancedLoadingService service;

    @Mock
    private TruckFactory truckFactory;

    @Mock
    private ParcelCountingService parcelCountingService;

    @Mock
    private ParcelQuantityRecordingService parcelQuantityRecordingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new BalancedLoadingService(truckFactory, parcelCountingService, parcelQuantityRecordingService);
    }

    @Test
    void packPackages_withCorrectInput_shouldReturnCorrectOutput() {
        List<ParcelForPackagingDto> parcels = new ArrayList<>(List.of(
                new ParcelForPackagingDto(1, 1, new char[][]{{'1'}}),
                new ParcelForPackagingDto(1, 1, new char[][]{{'1'}}),
                new ParcelForPackagingDto(1, 1, new char[][]{{'1'}}),
                new ParcelForPackagingDto(1, 3, new char[][]{{'3', '3', '3'}})
        ));

        Truck truck1 = new Truck(6, 6);
        Truck truck2 = new Truck(6, 6);
        when(truckFactory.createTrucks("6x6")).thenReturn(List.of(truck1, truck2));

        List<Truck> result = service.packPackages(parcels, "6x6");

        assertThat(result).hasSize(2);
        verify(parcelCountingService).countParcelsInTrucks(result);
        verify(parcelQuantityRecordingService).writeParcelCountToJsonFile(any());
    }

    @Test
    void packPackages_withDataThatDoesNotFitInOneTruck_shouldThrowPackingException() {
        List<ParcelForPackagingDto> parcels = new ArrayList<>(List.of(
                new ParcelForPackagingDto(3, 3, new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}}),
                new ParcelForPackagingDto(3, 3, new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}}),
                new ParcelForPackagingDto(3, 3, new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}}),
                new ParcelForPackagingDto(3, 3, new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}}),
                new ParcelForPackagingDto(3, 3, new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}}),
                new ParcelForPackagingDto(3, 3, new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}})
        ));

        Truck truck = new Truck(5, 5);
        when(truckFactory.createTrucks("1")).thenReturn(List.of(truck));

        assertThrows(PackingException.class, () -> service.packPackages(parcels, "1"));
    }
}