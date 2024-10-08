package ru.liga.consoleParcels.formatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.consoleParcels.dto.UnpackedTruckDto;
import ru.liga.consoleParcels.formatter.impl.DefaultPrintResultFormatter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultPrintResultFormatterTest {
    private PrintResultFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = new DefaultPrintResultFormatter();
    }

    @Test
    void transferUnpackingResultsToString_withValidUnPackedTrucks_shouldFormatCorrectly() {
        UnpackedTruckDto unpackedTruck1 = mock(UnpackedTruckDto.class);
        UnpackedTruckDto unpackedTruck2 = mock(UnpackedTruckDto.class);

        when(unpackedTruck1.getTruckId()).thenReturn(1);
        when(unpackedTruck1.getPackageLayout()).thenReturn(Arrays.asList(
                Arrays.asList("1", " ", " "),
                Arrays.asList("2", "2", " "),
                Arrays.asList("3", "3", "3")
        ));
        Map<String, Integer> packageCounts1 = new HashMap<>();
        packageCounts1.put("1", 1);
        packageCounts1.put("2", 2);
        packageCounts1.put("3", 3);
        when(unpackedTruck1.getPackageCountMap()).thenReturn(packageCounts1);

        when(unpackedTruck2.getTruckId()).thenReturn(2);
        when(unpackedTruck2.getPackageLayout()).thenReturn(Arrays.asList(
                Arrays.asList("4", " ", " "),
                Arrays.asList("5", "5", " "),
                Arrays.asList("6", "6", "6")
        ));
        Map<String, Integer> packageCounts2 = new HashMap<>();
        packageCounts2.put("4", 1);
        packageCounts2.put("5", 2);
        packageCounts2.put("6", 3);
        when(unpackedTruck2.getPackageCountMap()).thenReturn(packageCounts2);

        List<UnpackedTruckDto> unpackedTrucks = List.of(unpackedTruck1, unpackedTruck2);

        String result = formatter.transferUnpackingResultsToString(unpackedTrucks);

        String expectedOutput = """
                Грузовик 1:
                +1  +
                +22 +
                +333+
                +++++
                Количество посылок:
                Форма 1 - 1 шт.
                Форма 2 - 2 шт.
                Форма 3 - 3 шт.
                
                Грузовик 2:
                +4  +
                +55 +
                +666+
                +++++
                Количество посылок:
                Форма 4 - 1 шт.
                Форма 5 - 2 шт.
                Форма 6 - 3 шт.
                
                """;

        assertThat(result.toString()).isEqualTo(expectedOutput);
    }
}
