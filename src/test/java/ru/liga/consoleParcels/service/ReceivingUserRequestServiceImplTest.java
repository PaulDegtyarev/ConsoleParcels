package ru.liga.consoleParcels.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.consoleParcels.builder.PackagingCommandBuilder;
import ru.liga.consoleParcels.builder.impl.PackagingCommandBuilderImpl;
import ru.liga.consoleParcels.dto.PackagingParametersDto;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;
import ru.liga.consoleParcels.service.impl.ReceivingUserRequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.consoleParcels.model.UserCommand;

import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceivingUserRequestServiceImplTest {
    @Mock
    private PackagingCommandBuilderImpl packagingCommandBuilder;
    @Mock
    private Scanner scanner;

    @InjectMocks
    private ReceivingUserRequestServiceImpl receivingUserRequestService;

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
        UserCommand actualResponse = receivingUserRequestService.requestUserChoice();

        assertThat(expectedResult).isEqualTo(actualResponse);

        verify(scanner, times(2)).nextLine();
    }

    @Test
    void requestUserChoice_withOutOfBoundsInput_shouldRetryAndReturnValidChoice() {
        when(scanner.nextLine()).thenReturn("4", "3");

        UserCommand expectedResult = UserCommand.EXIT;
        UserCommand actualResponse = receivingUserRequestService.requestUserChoice();

        assertThat(expectedResult).isEqualTo(actualResponse);

        verify(scanner, times(2)).nextLine();
    }

    @Test
    void requestForFilePathToUnpackTruck_shouldReturnFilePath() {
        when(scanner.nextLine()).thenReturn("testFilePath");

        String expectedFilePath = "testFilePath";
        String actualFilePath = receivingUserRequestService.requestForFilePathToUnpackTruck();

        assertThat(expectedFilePath).isEqualTo(actualFilePath);

        verify(scanner, times(1)).nextLine();
    }

    @Test
    void requestParametersForPacking_shouldBuildParametersCorrectly() {
        when(packagingCommandBuilder.setNumberOfCars()).thenReturn(packagingCommandBuilder);
        when(packagingCommandBuilder.setInputFilePath()).thenReturn(packagingCommandBuilder);
        when(packagingCommandBuilder.setAlgorithmChoice()).thenReturn(packagingCommandBuilder);
        when(packagingCommandBuilder.setFilePathToWrite()).thenReturn(packagingCommandBuilder);

        PackagingParametersDto expectedParams = new PackagingParametersDto(3, "inputPath", UserAlgorithmChoice.MAX_SPACE, "outputPath");
        when(packagingCommandBuilder.build()).thenReturn(expectedParams);

        PackagingParametersDto actualParams = receivingUserRequestService.requestParametersForPacking();

        verify(packagingCommandBuilder).setNumberOfCars();
        verify(packagingCommandBuilder).setInputFilePath();
        verify(packagingCommandBuilder).setAlgorithmChoice();
        verify(packagingCommandBuilder).setFilePathToWrite();
        verify(packagingCommandBuilder).build();

        assertThat(actualParams).isEqualTo(expectedParams);
    }
}
