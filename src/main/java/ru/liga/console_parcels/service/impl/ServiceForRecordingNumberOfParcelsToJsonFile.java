package ru.liga.console_parcels.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.TruckParcelCountDto;
import ru.liga.console_parcels.service.RecordingService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Реализация сервиса для записи количества посылок в JSON файл.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ServiceForRecordingNumberOfParcelsToJsonFile implements RecordingService {
    private static final String JSON_FILENAME_TO_WRITE_QUANTITY = "data/trucks-with-number-of-parcels.json";
    @Autowired
    private final ObjectMapper objectMapper;

    /**
     * Записывает количество посылок в JSON файл.
     *
     * @param truckParcelCounts Список DTO с данными о количестве посылок в каждом грузовике.
     */
    @Override
    public void write(List<TruckParcelCountDto> truckParcelCounts) {
        try (FileWriter writer = new FileWriter(JSON_FILENAME_TO_WRITE_QUANTITY)) {
            objectMapper.writeValue(writer, truckParcelCounts);
        } catch (IOException e) {
            log.error("Ошибка при записи в JSON файл: ", e);
        }
    }
}
