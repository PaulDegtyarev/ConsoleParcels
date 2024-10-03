package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.exception.FileNotFoundException;
import ru.liga.consoleParcels.exception.PackageShapeException;
import ru.liga.consoleParcels.mapper.ParcelMapper;
import org.junit.jupiter.api.Test;
import ru.liga.consoleParcels.service.impl.DefaultPackageReader;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class DefaultPackageReaderTest {
    private DefaultPackageReader defaultPackageReader = new DefaultPackageReader();

    @Test
    void readPackages_withNonExistenceFile_shouldThrowFileNotFoundException() {
        String filePath = "/home/non-existent-file.txt";

        assertThatThrownBy(
                () -> defaultPackageReader.readPackages(filePath)).isInstanceOf(FileNotFoundException.class);
    }

    @Test
    void readPackages_withWrongData_shouldReturnPackageShapeException() {
        String filePath = "src/test/resources/input/wrong-input-data.txt";

        assertThatThrownBy(
                () -> defaultPackageReader.readPackages(filePath)
        ).isInstanceOf(PackageShapeException.class);
    }

//    @Test
//    void readPackages_withCorrectInput_shouldReturnCorrectOutput() {
//        String filePath = "src/test/resources/input/valid-input-data-for-optimized-loading-service.txt";
//
//        List<ParcelMapper> parcels = defaultPackageReader.readPackages(filePath);
//
//        assertThat(6).isEqualTo(parcels.size());
//
//        ParcelMapper firstParcel = parcels.get(0);
//        assertThat(3).isEqualTo(firstParcel.getHeight());
//        assertThat(3).isEqualTo(firstParcel.getWidth());
//        assertThat('9').isEqualTo(firstParcel.getId());
//
//        ParcelMapper secondParcel = parcels.get(1);
//        assertThat(2).isEqualTo(secondParcel.getHeight());
//        assertThat(3).isEqualTo(secondParcel.getWidth());
//        assertThat('6').isEqualTo(secondParcel.getId());
//
//        ParcelMapper thirdParcel = parcels.get(2);
//        assertThat(1).isEqualTo(thirdParcel.getHeight());
//        assertThat(5).isEqualTo(thirdParcel.getWidth());
//        assertThat('5').isEqualTo(thirdParcel.getId());
//
//        ParcelMapper fourthParcel = parcels.get(3);
//        assertThat(1).isEqualTo(fourthParcel.getHeight());
//        assertThat(1).isEqualTo(fourthParcel.getWidth());
//        assertThat('1').isEqualTo(fourthParcel.getId());
//
//        ParcelMapper fivethParcel = parcels.get(4);
//        assertThat(1).isEqualTo(fivethParcel.getHeight());
//        assertThat(1).isEqualTo(fivethParcel.getWidth());
//        assertThat('1').isEqualTo(fivethParcel.getId());
//
//        ParcelMapper sixthParcel = parcels.get(5);
//        assertThat(1).isEqualTo(sixthParcel.getHeight());
//        assertThat(3).isEqualTo(sixthParcel.getWidth());
//        assertThat('3').isEqualTo(sixthParcel.getId());
//    }
}
