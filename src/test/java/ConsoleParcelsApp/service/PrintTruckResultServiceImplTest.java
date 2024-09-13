package ConsoleParcelsApp.service;

import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.impl.PrintTruckResultServiceImpl;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PrintTruckResultServiceImplTest {
    @Test
    void printResults_withValidTrucks_printsCorrectOutput() {
        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck());
        trucks.add(new Truck());

        PrintTruckResultServiceImpl service = new PrintTruckResultServiceImpl();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));

        service.printResults(trucks);

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
