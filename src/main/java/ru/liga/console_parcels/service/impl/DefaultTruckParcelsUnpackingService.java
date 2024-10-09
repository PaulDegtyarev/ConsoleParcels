package ru.liga.console_parcels.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.UnpackedTruckDto;
import ru.liga.console_parcels.service.TruckParcelsUnpackingService;
import ru.liga.console_parcels.service.FileUnpackakingService;

import java.util.List;

/**
 * Реализация менеджера для распаковки грузовиков.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class DefaultTruckParcelsUnpackingService implements TruckParcelsUnpackingService {
    private final FileUnpackakingService fileUnpackakingService;

    /**
     * Распаковывает посылки из грузовиков и формирует результаты.
     *
     * @param truckFilePath Путь к файлу с данными грузовиков.
     * @return список распакованных грузовиков.
     */
    @Override
    public List<UnpackedTruckDto> unpack(String truckFilePath) {
        log.info("Начало процесса распаковки");

        List<UnpackedTruckDto> unpackedTrucks = fileUnpackakingService.unpackTruck(truckFilePath);
        log.info("Распаковка завершена. Распаковано {} машин", unpackedTrucks.size());
        return unpackedTrucks;
    }
}
