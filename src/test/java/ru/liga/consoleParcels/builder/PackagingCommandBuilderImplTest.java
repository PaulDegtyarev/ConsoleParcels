package ru.liga.consoleParcels.builder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.consoleParcels.builder.impl.PackagingCommandBuilderImpl;
import ru.liga.consoleParcels.dto.PackagingParametersDto;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;

import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PackagingCommandBuilderImplTest {
    @Mock
    private Scanner scanner;

    @InjectMocks
    private PackagingCommandBuilderImpl packagingCommandBuilder;

    @Test
    void setNumberOfCars_withValidInput_shouldSetCorrectValue() {
        when(scanner.nextLine()).thenReturn("3");

        packagingCommandBuilder.setNumberOfCars();

        PackagingParametersDto result = packagingCommandBuilder.build();
        assertThat(result.getNumberOfCars()).isEqualTo(3);
    }

    @Test
    void setNumberOfCars_withInvalidThenValidInput_shouldRetryAndSetCorrectValue() {
        when(scanner.nextLine()).thenReturn("abc", "2");

        packagingCommandBuilder.setNumberOfCars();

        PackagingParametersDto result = packagingCommandBuilder.build();
        assertThat(result.getNumberOfCars()).isEqualTo(2);

        verify(scanner, times(2)).nextLine();
    }

    @Test
    void setInputFilePath_withValidInput_shouldSetCorrectValue() {
        when(scanner.nextLine()).thenReturn("input/file/path.txt");

        packagingCommandBuilder.setInputFilePath();

        PackagingParametersDto result = packagingCommandBuilder.build();
        assertThat(result.getInputFilePath()).isEqualTo("input/file/path.txt");
    }

    @Test
    void setAlgorithmChoice_withValidInput_shouldSetCorrectAlgorithm() {
        when(scanner.nextLine()).thenReturn("1");

        packagingCommandBuilder.setAlgorithmChoice();

        PackagingParametersDto result = packagingCommandBuilder.build();
        assertThat(result.getAlgorithmChoice()).isEqualTo(UserAlgorithmChoice.MAX_SPACE);
    }

    @Test
    void setAlgorithmChoice_withInvalidThenValidInput_shouldRetryAndSetCorrectAlgorithm() {
        when(scanner.nextLine()).thenReturn("5", "2");

        packagingCommandBuilder.setAlgorithmChoice();

        PackagingParametersDto result = packagingCommandBuilder.build();
        assertThat(result.getAlgorithmChoice()).isEqualTo(UserAlgorithmChoice.EVEN_LOADING);

        verify(scanner, times(2)).nextLine();
    }

    @Test
    void setFilePathToWrite_withValidInput_shouldSetCorrectValue() {
        when(scanner.nextLine()).thenReturn("output/file/path.txt");

        packagingCommandBuilder.setFilePathToWrite();

        PackagingParametersDto result = packagingCommandBuilder.build();
        assertThat(result.getFilePathToWrite()).isEqualTo("output/file/path.txt");
    }

    @Test
    void build_shouldReturnCorrectPackagingParametersDto() {
        when(scanner.nextLine()).thenReturn("3", "input/file/path.txt", "1", "output/file/path.txt");

        packagingCommandBuilder
                .setNumberOfCars()
                .setInputFilePath()
                .setAlgorithmChoice()
                .setFilePathToWrite();

        PackagingParametersDto result = packagingCommandBuilder.build();

        assertThat(result.getNumberOfCars()).isEqualTo(3);
        assertThat(result.getInputFilePath()).isEqualTo("input/file/path.txt");
        assertThat(result.getAlgorithmChoice()).isEqualTo(UserAlgorithmChoice.MAX_SPACE);
        assertThat(result.getFilePathToWrite()).isEqualTo("output/file/path.txt");
    }
}
