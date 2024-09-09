package ConsoleParcelsApp.util;

import ConsoleParcelsApp.model.Parcel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PackageReaderTest {
    private PackageReader packageReader = new PackageReader();

    @Test
    void readPackagesShouldThrowIOExceptionWhenFileDoesNotExist() {
        String filePath = "/home/non-existent-file.txt";

        assertThrows(
                IOException.class,
                () -> packageReader.readPackages(filePath));
    }

    @Test
    void readPackagesShouldReturnIllegalArgumentExceptionWhenDataIsWrong() {
        String filePath = "src/test/resources/input/wrong-input-data.txt";

        assertThrows(
                IllegalArgumentException.class,
                () -> packageReader.readPackages(filePath));
    }

    @Test
    public void readPackagesSuccess() throws IOException {
        String filePath = "src/test/resources/input/valid-input-data.txt";

        List<Parcel> parcels = packageReader.readPackages(filePath);

        assertEquals(6, parcels.size());

        Parcel firstParcel = parcels.get(0);
        assertEquals(3, firstParcel.getHeight());
        assertEquals(3, firstParcel.getWidth());
        assertEquals('9', firstParcel.getId());

        Parcel secondParcel = parcels.get(1);
        assertEquals(2, secondParcel.getHeight());
        assertEquals(3, secondParcel.getWidth());
        assertEquals('6', secondParcel.getId());

        Parcel thirdParcel = parcels.get(2);
        assertEquals(1, thirdParcel.getHeight());
        assertEquals(5, thirdParcel.getWidth());
        assertEquals('5', thirdParcel.getId());

        Parcel fourthParcel = parcels.get(3);
        assertEquals(1, fourthParcel.getHeight());
        assertEquals(1, fourthParcel.getWidth());
        assertEquals('1', fourthParcel.getId());

        Parcel fivethParcel = parcels.get(4);
        assertEquals(1, fivethParcel.getHeight());
        assertEquals(1, fivethParcel.getWidth());
        assertEquals('1', fivethParcel.getId());

        Parcel sixthParcel = parcels.get(5);
        assertEquals(1, sixthParcel.getHeight());
        assertEquals(3, sixthParcel.getWidth());
        assertEquals('3', sixthParcel.getId());
    }
}
