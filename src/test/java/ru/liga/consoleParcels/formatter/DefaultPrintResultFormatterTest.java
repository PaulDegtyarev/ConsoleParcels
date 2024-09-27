package ru.liga.consoleParcels.formatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.formatter.impl.DefaultPrintResultFormatter;
import ru.liga.consoleParcels.model.Truck;

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
    void transferPackagingResultsToConsole_withValidTrucks_shouldFormatCorrectly() {
        Truck truck1 = mock(Truck.class);
        Truck truck2 = mock(Truck.class);

        when(truck1.getSpace()).thenReturn(new char[][]{
                {'1', ' ', ' '},
                {'2', '2', ' '},
                {'3', '3', '3'}
        });

        when(truck2.getSpace()).thenReturn(new char[][]{
                {'4', ' ', ' '},
                {'5', '5', ' '},
                {'6', '6', '6'}
        });

        List<Truck> trucks = List.of(truck1, truck2);

        StringBuilder result = formatter.transferPackagingResultsToConsole(trucks);

        String expectedOutput = """
                Грузовик 1:
                +1  +
                +22 +
                +333+
                ++++++++
                
                Грузовик 2:
                +4  +
                +55 +
                +666+
                ++++++++
                
                """;

        assertThat(result.toString()).isEqualTo(expectedOutput);
    }

    @Test
    void transferUnpackingResultsToConsole_withValidUnPackedTrucks_shouldFormatCorrectly() {
        UnPackedTruckDto unpackedTruck1 = mock(UnPackedTruckDto.class);
        UnPackedTruckDto unpackedTruck2 = mock(UnPackedTruckDto.class);

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
        when(unpackedTruck1.getPackageCounts()).thenReturn(packageCounts1);

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
        when(unpackedTruck2.getPackageCounts()).thenReturn(packageCounts2);

        List<UnPackedTruckDto> unpackedTrucks = List.of(unpackedTruck1, unpackedTruck2);

        StringBuilder result = formatter.transferUnpackingResultsToConsole(unpackedTrucks);

        String expectedOutput = """
                Грузовик 1:
                +1  +
                +22 +
                +333+
                ++++++++
                Количество посылок:
                1 - 1 шт.
                2 - 2 шт.
                3 - 3 шт.
                
                Грузовик 2:
                +4  +
                +55 +
                +666+
                ++++++++
                Количество посылок:
                4 - 1 шт.
                5 - 2 шт.
                6 - 3 шт.
                
                """;

        assertThat(result.toString()).isEqualTo(expectedOutput);
    }
}
