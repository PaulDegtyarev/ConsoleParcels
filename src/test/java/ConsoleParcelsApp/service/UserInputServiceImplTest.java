package ConsoleParcelsApp.service;

import ConsoleParcelsApp.service.impl.UserInputServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class UserInputServiceImplTest {
    private UserInputService userInputService;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        scanner = Mockito.mock(Scanner.class);
        userInputService = new UserInputServiceImpl(scanner);
    }

    @Test
    void requestForNumberOfCars_withValidInput_shouldReturnValidOutput() {
        when(scanner.nextLine()).thenReturn("5");

        int expectedResponse = userInputService.requestForNumberOfCars();

        assertThat(expectedResponse).isEqualTo(5);
    }

    @Test
    void requestForNumberOfCars_withWrongInputThanCorrectInput_shouldStartTwoTimes() {
        when(scanner.nextLine()).thenReturn("abc", "5");

        int expectedResponse = userInputService.requestForNumberOfCars();

        verify(scanner, times(2)).nextLine();

        assertThat(expectedResponse).isEqualTo(5);
    }

    @Test
    void requestForFilePath_withValidInput_shouldReturnValidOutput() {
        when(scanner.nextLine()).thenReturn("/src/test/resources/input/valid-input-data.txt");

        String expectedResponse = userInputService.requestForFilePath();

        assertThat(expectedResponse).isEqualTo("/src/test/resources/input/valid-input-data.txt");
    }

    @Test
    void requestForAlgorithmChoice_withValidInput_shouldReturnValidOutput() {
        when(scanner.nextLine()).thenReturn("1");

        int expectedResponse = userInputService.requestForAlgorithmChoice();

        assertThat(expectedResponse).isEqualTo(1);
    }

    @Test
    void requestForAlgorithmChoice_withInvalidInputThanCorrectInput_shouldStartTwoTimes() {
        when(scanner.nextLine()).thenReturn("abc", "5");

        int expectedResponse = userInputService.requestForAlgorithmChoice();

        verify(scanner, times(2)).nextLine();

        assertThat(expectedResponse).isEqualTo(5);
    }
}
