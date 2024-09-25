package ru.liga.consoleParcels.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TruckTest {
    private Truck truck;

    @BeforeEach
    void setUp() {
        truck = new Truck();
    }

    @Test
    void findPosition_withCorrectInput_shouldReturnCorrectPoints() {
        Parcel smallParcel = new Parcel("1");
        Parcel mediumParcel = new Parcel("22\n22");

        Optional<Point> positionSmall = truck.findPosition(smallParcel);
        assertThat(positionSmall.get().getX()).isEqualTo(0);
        assertThat(positionSmall.get().getY()).isEqualTo(5);

        Optional<Point> positionMedium = truck.findPosition(mediumParcel);
        assertThat(positionMedium.get().getX()).isEqualTo(0);
        assertThat(positionMedium.get().getY()).isEqualTo(4);
    }

    @Test
    void toConsoleFormat_withCorrectInput_shouldReturnCorrectOutput() {
        Parcel parcel = new Parcel("1\n22");
        truck.place(parcel, 0, 0);

        String actualOutput = truck.toConsoleFormat();

        String expectedOutput = "+      +\n" +
                "+1     +\n" +
                "+22    +\n" +
                "+      +\n" +
                "+      +\n" +
                "+      +\n" +
                "+      +\n" +
                "++++++++\n" +
                "\n";

        assertThat(actualOutput).isEqualTo(expectedOutput);
    }
}
