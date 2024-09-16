package ConsoleParcelsApp.service;

import ConsoleParcelsApp.service.impl.PackagingInputServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class PackagingInputServiceImplTest {
    private PackagingInputService packagingInputService;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        scanner = Mockito.mock(Scanner.class);
        packagingInputService = new PackagingInputServiceImpl(scanner);
    }

    @Test
    void requestForNumberOfCars_withValidInput_shouldReturnValidOutput() {
        when(scanner.nextLine()).thenReturn("5");

        int expectedResponse = packagingInputService.requestForNumberOfCars();

        assertThat(expectedResponse).isEqualTo(5);
    }

    @Test
    void requestForNumberOfCars_withWrongInputThanCorrectInput_shouldStartTwoTimes() {
        when(scanner.nextLine()).thenReturn("abc", "5");

        int expectedResponse = packagingInputService.requestForNumberOfCars();

        verify(scanner, times(2)).nextLine();

        assertThat(expectedResponse).isEqualTo(5);
    }

    @Test
    void requestForFilePath_withValidInput_shouldReturnValidOutput() {
        when(scanner.nextLine()).thenReturn("/src/test/resources/input/valid-input-data-for-optimized-loading-service.txt");

        String expectedResponse = packagingInputService.requestForFilePath();

        assertThat(expectedResponse).isEqualTo("/src/test/resources/input/valid-input-data-for-optimized-loading-service.txt");
    }

    @Test
    void requestForAlgorithmChoice_withValidInput_shouldReturnValidOutput() {
        when(scanner.nextLine()).thenReturn("1");

        int expectedResponse = packagingInputService.requestForAlgorithmChoice();

        assertThat(expectedResponse).isEqualTo(1);
    }

    @Test
    void requestForAlgorithmChoice_withInvalidInputThanCorrectInput_shouldStartTwoTimes() {
        when(scanner.nextLine()).thenReturn("abc", "5");

        int expectedResponse = packagingInputService.requestForAlgorithmChoice();

        verify(scanner, times(2)).nextLine();

        assertThat(expectedResponse).isEqualTo(5);
    }
}
