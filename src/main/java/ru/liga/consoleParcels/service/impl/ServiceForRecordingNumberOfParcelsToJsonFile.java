package ru.liga.consoleParcels.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.dto.ParcelCountDto;
import ru.liga.consoleParcels.service.ParcelQuantityRecordingService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class ServiceForRecordingNumberOfParcelsToJsonFile implements ParcelQuantityRecordingService {
    private final String JSON_FILENAME_TO_WRITE_QUANTITY = "data/trucks-with-number-of-parcels.json";

    @Override
    public void writeParcelCountToJsonFile(Map<String, Integer> parcelCountByShape) {
        List<ParcelCountDto> parcelCounts = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : parcelCountByShape.entrySet()) {
            parcelCounts.add(new ParcelCountDto(entry.getKey(), entry.getValue()));
        }

        try (FileWriter writer = new FileWriter(JSON_FILENAME_TO_WRITE_QUANTITY)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, parcelCounts);
        } catch (IOException e) {
            log.error("Ошибка при записи в JSON файл: ", e);
        }
    }
}
