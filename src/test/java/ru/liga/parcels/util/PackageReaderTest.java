package ru.liga.parcels.util;

import ru.liga.parcels.exception.FileNotFoundException;
import ru.liga.parcels.exception.PackageShapeException;
import ru.liga.parcels.model.Parcel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class PackageReaderTest {
    private PackageReader packageReader = new PackageReader();

    @Test
    void readPackages_withNonExistenceFile_shouldThrowFileNotFoundException() {
        String filePath = "/home/non-existent-file.txt";

        assertThatThrownBy(
                () -> packageReader.readPackages(filePath)).isInstanceOf(FileNotFoundException.class);
    }

    @Test
    void readPackages_withWrongData_shouldReturnPackageShapeException() {
        String filePath = "src/test/resources/input/wrong-input-data.txt";

        assertThatThrownBy(
                () -> packageReader.readPackages(filePath)
        ).isInstanceOf(PackageShapeException.class);
    }

    @Test
    void readPackages_withCorrectInput_shouldReturnCorrectOutput() {
        String filePath = "src/test/resources/input/valid-input-data-for-optimized-loading-service.txt";

        List<Parcel> parcels = packageReader.readPackages(filePath);

        assertThat(6).isEqualTo(parcels.size());

        Parcel firstParcel = parcels.get(0);
        assertThat(3).isEqualTo(firstParcel.getHeight());
        assertThat(3).isEqualTo(firstParcel.getWidth());
        assertThat('9').isEqualTo(firstParcel.getId());

        Parcel secondParcel = parcels.get(1);
        assertThat(2).isEqualTo(secondParcel.getHeight());
        assertThat(3).isEqualTo(secondParcel.getWidth());
        assertThat('6').isEqualTo(secondParcel.getId());

        Parcel thirdParcel = parcels.get(2);
        assertThat(1).isEqualTo(thirdParcel.getHeight());
        assertThat(5).isEqualTo(thirdParcel.getWidth());
        assertThat('5').isEqualTo(thirdParcel.getId());

        Parcel fourthParcel = parcels.get(3);
        assertThat(1).isEqualTo(fourthParcel.getHeight());
        assertThat(1).isEqualTo(fourthParcel.getWidth());
        assertThat('1').isEqualTo(fourthParcel.getId());

        Parcel fivethParcel = parcels.get(4);
        assertThat(1).isEqualTo(fivethParcel.getHeight());
        assertThat(1).isEqualTo(fivethParcel.getWidth());
        assertThat('1').isEqualTo(fivethParcel.getId());

        Parcel sixthParcel = parcels.get(5);
        assertThat(1).isEqualTo(sixthParcel.getHeight());
        assertThat(3).isEqualTo(sixthParcel.getWidth());
        assertThat('3').isEqualTo(sixthParcel.getId());
    }
}
