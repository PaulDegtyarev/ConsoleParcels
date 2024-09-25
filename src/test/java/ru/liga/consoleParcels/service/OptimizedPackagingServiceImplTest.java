package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.impl.TruckFactoryImpl;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.impl.OptimizedPackagingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OptimizedPackagingServiceImplTest {
    private OptimizedPackagingServiceImpl service;

    @BeforeEach
    public void setUp() {
        service = new OptimizedPackagingServiceImpl(
                new TruckFactoryImpl());
    }

    @Test
    void packPackages_withCorrectInput_shouldReturnCorrectOutput() {
        List<Parcel> parcels = new ArrayList<>(List.of(
                new Parcel("1"),
                new Parcel("22")
        ));

        List<Truck> expectedResult = service.packPackages(parcels, 1);

        assertThat(expectedResult.size()).isEqualTo(1);
    }

    @Test
    void packPackage_withDataThatDoesNotFitInOneTruck_shouldThrowPackingException() {
        List<Parcel> parcels = new ArrayList<>(List.of(
                new Parcel("999\n999\n999"),
                new Parcel("999\n999\n999"),
                new Parcel("999\n999\n999"),
                new Parcel("999\n999\n999"),
                new Parcel("999\n999\n999"),
                new Parcel("999\n999\n999")
        ));

        assertThrows(PackingException.class, () -> service.packPackages(parcels, 1));
    }
}
