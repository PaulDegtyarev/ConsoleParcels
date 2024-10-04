//package ru.liga.consoleParcels.service;
//
//import ru.liga.consoleParcels.dto.UnPackedTruckDto;
//import ru.liga.consoleParcels.exception.FileReadException;
//import ru.liga.consoleParcels.factory.DelimeterFactory;
//import ru.liga.consoleParcels.factory.impl.DefaultDelimeterFactory;
//import ru.liga.consoleParcels.service.impl.DefaultUnPackagingService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//public class DefaultUnPackagingServiceTest {
//    private UnPackagingService unPackagingService;
//    private DelimeterFactory delimeterFactory;
//
//    @BeforeEach
//    void setUp() {
//        delimeterFactory = new DefaultDelimeterFactory();
//        unPackagingService = new DefaultUnPackagingService(delimeterFactory);
//    }
//
//    @Test
//    void unpackTruck_withValidInput_shouldReturnValidOutput() {
//        String filePath = "src/test/resources/input/truck-to-test-unpacking.json";
//
//        List<UnPackedTruckDto> result = unPackagingService.unpackTruck(filePath);
//
//        assertThat(result.size()).isEqualTo(1);
//    }
//
//    @Test
//    void unpackTruck_withWrongFilePath_shouldReturnFileReadException() {
//        String filePath = "non_existent_file.json";
//        assertThrows(FileReadException.class, () -> unPackagingService.unpackTruck(filePath));
//    }
//}
