package ConsoleParcelsApp.service;

import ConsoleParcelsApp.exception.WrongInputForNumberCarException;
import ConsoleParcelsApp.service.impl.UserInputServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

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

        int result = userInputService.requestForNumberOfCars();

        assertThat(result).isEqualTo(5);
    }

    @Test
    void requestForNumberOfCars_withWrongInput_shouldReturnWrongInputForNumberCarException() {
        when(scanner.nextLine()).thenReturn("abc");

        assertThatThrownBy(
                () -> userInputService.requestForNumberOfCars()
        ).isInstanceOf(WrongInputForNumberCarException.class);
    }
}
