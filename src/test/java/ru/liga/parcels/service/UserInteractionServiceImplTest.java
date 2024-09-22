package ru.liga.parcels.service;

import ru.liga.parcels.service.impl.UserInteractionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.parcels.util.UserAlgorithmChoice;

import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class UserInteractionServiceImplTest {
    private UserInteractionService userInteractionService;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        scanner = Mockito.mock(Scanner.class);
        userInteractionService = new UserInteractionServiceImpl(scanner);
    }

    @Test
    void requestForNumberOfCars_withValidInput_shouldReturnValidOutput() {
        when(scanner.nextLine()).thenReturn("5");

        int expectedResponse = userInteractionService.requestForNumberOfCars();

        assertThat(expectedResponse).isEqualTo(5);
    }

    @Test
    void requestForNumberOfCars_withWrongInputThanCorrectInput_shouldStartTwoTimes() {
        when(scanner.nextLine()).thenReturn("abc", "5");

        int expectedResponse = userInteractionService.requestForNumberOfCars();

        verify(scanner, times(2)).nextLine();

        assertThat(expectedResponse).isEqualTo(5);
    }

    @Test
    void requestForInputFilePath_withValidInput_shouldReturnValidOutput() {
        String filePath = "/src/test/resources/input/valid-input-data-for-optimized-loading-service.txt";
        when(scanner.nextLine()).thenReturn(filePath);

        String expectedResponse = userInteractionService.requestForInputFilePath();

        assertThat(expectedResponse).isEqualTo(filePath);
    }

    @Test
    void requestForAlgorithmChoice_withValidInput_shouldReturnValidOutput() {
        when(scanner.nextLine()).thenReturn("1");

        UserAlgorithmChoice expectedResponse = userInteractionService.requestForAlgorithmChoice();

        assertThat(expectedResponse).isEqualTo(UserAlgorithmChoice.MAX_SPACE);
    }

    @Test
    void requestForAlgorithmChoice_withInvalidInputThanCorrectInput_shouldStartTwoTimes() {
        when(scanner.nextLine()).thenReturn("abc", "2");

        UserAlgorithmChoice expectedResponse = userInteractionService.requestForAlgorithmChoice();

        verify(scanner, times(2)).nextLine();

        assertThat(expectedResponse).isEqualTo(UserAlgorithmChoice.EVEN_LOADING);
    }
}
