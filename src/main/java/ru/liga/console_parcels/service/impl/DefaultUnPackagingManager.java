package ru.liga.console_parcels.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.UnpackedTruckDto;
import ru.liga.console_parcels.formatter.ResultFormatter;
import ru.liga.console_parcels.service.UnPackagingManager;
import ru.liga.console_parcels.service.UnPackagingService;

import java.util.List;

/**
 * Реализация менеджера для распаковки грузовиков.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class DefaultUnPackagingManager implements UnPackagingManager {
    private final UnPackagingService unPackagingService;
    private final ResultFormatter resultFormatter;

    /**
     * Распаковывает посылки из грузовиков и формирует результаты.
     *
     * @param truckFilePath       Путь к файлу с данными грузовиков.
     * @return Строка с результатами распаковки.
     */
    @Override
    public String unpackParcels(String truckFilePath) {
        log.info("Начало процесса распаковки");

        List<UnpackedTruckDto> unpackedTrucks = unPackagingService.unpackTruck(truckFilePath);
        log.info("Распаковка завершена. Распаковано {} машин", unpackedTrucks.size());

        log.info("Начало печати результатов распаковки для {} грузовиков", unpackedTrucks.size());

        return resultFormatter.convertUnpackingResultsToString(unpackedTrucks);
    }
}
