package ConsoleParcelsApp.service;

import ConsoleParcelsApp.service.impl.ReceivingUserRequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class ReceivingUserRequestServiceImplTest {
    private ReceivingUserRequestService receivingUserRequestService;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        scanner = Mockito.mock(Scanner.class);
        receivingUserRequestService = new ReceivingUserRequestServiceImpl(scanner);
    }

    @Test
    void requestUserChoice_withValidInput_shouldReturnValidOutput() {
        when(scanner.nextLine()).thenReturn("1");

        int expectedResponse = 1;
        int actualResponse = receivingUserRequestService.requestUserChoice();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void requestUserChoice_withInvalidInputThenValidInput_shouldStartTwoTimesAndReturnValidChoice() {
        when(scanner.nextLine()).thenReturn("invalid", "2");

        int expectedResult = 2;
        int choice = receivingUserRequestService.requestUserChoice();

        assertThat(expectedResult).isEqualTo(choice);
    }
}
