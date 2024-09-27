package ru.liga.consoleParcels.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.liga.consoleParcels.exception.FileNotFoundException;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.impl.TruckToJsonWriterServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TruckToJsonWriterServiceImplTest {
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TruckToJsonWriterServiceImpl truckToJsonWriterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void writeTruckToJson_withValidInput_shouldWriteToFile() throws IOException {
        Truck truck = mock(Truck.class);
        when(truck.getSpace()).thenReturn(new char[][]{
                {'1', ' ', ' '},
                {'2', '2', ' '},
                {'3', '3', '3'}
        });

        File filePath = new File("src/test/resources/input/test-json-to-write.json");

        truckToJsonWriterService.writeTruckToJson(List.of(truck), filePath.toString());

        verify(objectMapper, times(1)).writeValue(any(OutputStream.class), any());

        assertThat(Files.exists(filePath.toPath())).isTrue();
    }

    @Test
    void writeTruckToJson_withNonExistentFile_shouldThrowFileNotFoundException() {
        Truck truck = mock(Truck.class);
        String invalidFilePath = "some/non_existent_directory/trucks.json";

        assertThrows(FileNotFoundException.class, () -> truckToJsonWriterService.writeTruckToJson(List.of(truck), invalidFilePath));
    }
}
