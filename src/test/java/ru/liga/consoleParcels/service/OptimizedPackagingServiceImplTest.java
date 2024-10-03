package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.impl.DefaultTruckFactory;
import ru.liga.consoleParcels.mapper.ParcelMapper;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.impl.OptimizedPackagingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


//public class OptimizedPackagingServiceImplTest {
//    private OptimizedPackagingServiceImpl service;
//
//    @BeforeEach
//    public void setUp() {
//        service = new OptimizedPackagingServiceImpl(
//                new DefaultTruckFactory());
//    }
//
//    @Test
//    void packPackages_withCorrectInput_shouldReturnCorrectOutput() {
//        List<ParcelMapper> parcels = new ArrayList<>(List.of(
//                new ParcelMapper("1"),
//                new ParcelMapper("22")
//        ));
//
//        List<Truck> expectedResult = service.packPackages(parcels, 1);
//
//        assertThat(expectedResult.size()).isEqualTo(1);
//    }
//
//    @Test
//    void packPackage_withDataThatDoesNotFitInOneTruck_shouldThrowPackingException() {
//        List<ParcelMapper> parcels = new ArrayList<>(List.of(
//                new ParcelMapper("999\n999\n999"),
//                new ParcelMapper("999\n999\n999"),
//                new ParcelMapper("999\n999\n999"),
//                new ParcelMapper("999\n999\n999"),
//                new ParcelMapper("999\n999\n999"),
//                new ParcelMapper("999\n999\n999")
//        ));
//
//        assertThrows(PackingException.class, () -> service.packPackages(parcels, 1));
//    }
//}
