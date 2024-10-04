package ru.liga.consoleParcels.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.dto.TruckParcelCountDto;
import ru.liga.consoleParcels.service.ParcelQuantityRecordingService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Log4j2
public class ServiceForRecordingNumberOfParcelsToJsonFile implements ParcelQuantityRecordingService {
    private final String JSON_FILENAME_TO_WRITE_QUANTITY = "data/trucks-with-number-of-parcels.json";

    @Override
    public void writeParcelCountToJsonFile(List<TruckParcelCountDto> truckParcelCounts) {
        try (FileWriter writer = new FileWriter(JSON_FILENAME_TO_WRITE_QUANTITY)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, truckParcelCounts);
        } catch (IOException e) {
            log.error("Ошибка при записи в JSON файл: ", e);
        }
    }
}
