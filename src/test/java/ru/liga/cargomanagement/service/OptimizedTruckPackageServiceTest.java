package ru.liga.cargomanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.liga.cargomanagement.dto.ParcelForPackagingDto;
import ru.liga.cargomanagement.entity.Truck;
import ru.liga.cargomanagement.exception.PackingException;
import ru.liga.cargomanagement.factory.TruckFactory;
import ru.liga.cargomanagement.service.impl.OptimizedPackagingService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class OptimizedTruckPackageServiceTest {
    @InjectMocks
    private OptimizedPackagingService service;

    @Mock
    private TruckFactory truckFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new OptimizedPackagingService(truckFactory);
    }

    @Test
    void packPackages_withCorrectInput_shouldReturnCorrectOutput() {
        ParcelForPackagingDto parcel1 = new ParcelForPackagingDto(1, 1, new char[][]{{'1'}});
        ParcelForPackagingDto parcel2 = new ParcelForPackagingDto(1, 1, new char[][]{{'2'}});
        List<ParcelForPackagingDto> parcels = new ArrayList<>(List.of(parcel1, parcel2));

        Truck truck = new Truck(4, 4);
        when(truckFactory.createTrucks("4x4")).thenReturn(List.of(truck));

        List<Truck> result = service.packPackages(parcels, "4x4");

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void packPackage_withDataThatDoesNotFitInOneTruck_shouldThrowPackingException() {
        ParcelForPackagingDto parcel = new ParcelForPackagingDto(3, 3, new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}});
        List<ParcelForPackagingDto> parcels = new ArrayList<>(List.of(parcel, parcel, parcel, parcel, parcel, parcel));

        Truck truck = new Truck(2, 2);
        when(truckFactory.createTrucks("2x2")).thenReturn(List.of(truck));

        assertThrows(PackingException.class, () -> service.packPackages(parcels, "2x2"));
    }
}
