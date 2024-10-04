package ru.liga.consoleParcels.service;

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
