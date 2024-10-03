//package ru.liga.consoleParcels.service;
//
//import ru.liga.consoleParcels.exception.PackingException;
//import ru.liga.consoleParcels.factory.impl.DefaultTruckFactory;
//import ru.liga.consoleParcels.mapper.ParcelMapper;
//import ru.liga.consoleParcels.model.Truck;
//import ru.liga.consoleParcels.service.impl.BalancedLoadingServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//public class BalancedLoadingServiceImplTest {
//    private BalancedLoadingServiceImpl service;
//
//    @BeforeEach
//    public void setUp() {
//        service = new BalancedLoadingServiceImpl(
//                new DefaultTruckFactory()
//        );
//    }
//
//    @Test
//    public void packPackages_withCorrectInput_shouldReturnCorrectOutput() {
//        List<ParcelMapper> parcels = new ArrayList<>(List.of(
//                new ParcelMapper("1"),
//                new ParcelMapper("1"),
//                new ParcelMapper("1"),
//                new ParcelMapper("333")
//        ));
//
//        List<Truck> expectedResult = service.packPackages(parcels, 2);
//
//        assertThat(expectedResult.size()).isEqualTo(2);
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