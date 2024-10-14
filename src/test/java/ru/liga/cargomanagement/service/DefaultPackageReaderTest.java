package ru.liga.cargomanagement.service;

import org.junit.jupiter.api.Test;
import ru.liga.cargomanagement.dto.ParcelForPackagingDto;
import ru.liga.cargomanagement.exception.FileNotFoundException;
import ru.liga.cargomanagement.service.impl.DefaultPackageReader;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class DefaultPackageReaderTest {
    private DefaultPackageReader defaultPackageReader = new DefaultPackageReader();

    @Test
    void read_withNonExistenceFile_shouldThrowFileNotFoundException() {
        String filePath = "/home/non-existent-file.txt";

        assertThatThrownBy(
                () -> defaultPackageReader.read(filePath)).isInstanceOf(FileNotFoundException.class);
    }

    @Test
    void read_withCorrectInput_shouldReturnCorrectOutput() {
        String filePath = "src/test/resources/input/valid-input-data-for-optimized-loading-service.txt";

        List<ParcelForPackagingDto> parcels = defaultPackageReader.read(filePath);

        assertThat(6).isEqualTo(parcels.size());

        ParcelForPackagingDto firstParcel = parcels.get(0);
        assertThat(3).isEqualTo(firstParcel.getHeight());
        assertThat(3).isEqualTo(firstParcel.getWidth());

        ParcelForPackagingDto secondParcel = parcels.get(1);
        assertThat(2).isEqualTo(secondParcel.getHeight());
        assertThat(3).isEqualTo(secondParcel.getWidth());


        ParcelForPackagingDto thirdParcel = parcels.get(2);
        assertThat(1).isEqualTo(thirdParcel.getHeight());
        assertThat(5).isEqualTo(thirdParcel.getWidth());


        ParcelForPackagingDto fourthParcel = parcels.get(3);
        assertThat(1).isEqualTo(fourthParcel.getHeight());
        assertThat(1).isEqualTo(fourthParcel.getWidth());


        ParcelForPackagingDto fivethParcel = parcels.get(4);
        assertThat(1).isEqualTo(fivethParcel.getHeight());
        assertThat(1).isEqualTo(fivethParcel.getWidth());


        ParcelForPackagingDto sixthParcel = parcels.get(5);
        assertThat(1).isEqualTo(sixthParcel.getHeight());
        assertThat(3).isEqualTo(sixthParcel.getWidth());

    }
}
