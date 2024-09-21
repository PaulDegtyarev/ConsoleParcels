package ru.liga.parcels.service;

import ru.liga.parcels.model.Truck;
import ru.liga.parcels.service.impl.PrintResultServiceImpl;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PrintResultServiceImplTest {
    @Test
    void printPackagingResults_withValidTrucks_shouldPrintCorrectOutput() {
        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck());
        trucks.add(new Truck());

        PrintResultServiceImpl service = new PrintResultServiceImpl();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));

        service.printPackagingResults(trucks);

        System.setOut(originalSystemOut);

        String actualOutput = outputStream.toString().trim();

        String expectedOutput =
                "Truck 1:\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "++++++++\n\n" +
                        "Truck 2:\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "+      +\n" +
                        "++++++++";

        assertThat(expectedOutput).isEqualTo(actualOutput);
    }
}
