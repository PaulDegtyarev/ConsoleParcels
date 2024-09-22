package ru.liga.parcels.service;

import ru.liga.parcels.service.impl.ReceivingUserRequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.parcels.util.UserCommand;

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

        UserCommand expectedResponse = UserCommand.PACK;
        UserCommand actualResponse = receivingUserRequestService.requestUserChoice();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void requestUserChoice_withInvalidInputThenValidInput_shouldStartTwoTimesAndReturnValidChoice() {
        when(scanner.nextLine()).thenReturn("invalid", "2");

        UserCommand expectedResult = UserCommand.UNPACK;
        UserCommand choice = receivingUserRequestService.requestUserChoice();

        assertThat(expectedResult).isEqualTo(choice);
    }
}
