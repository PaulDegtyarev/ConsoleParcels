package ru.liga.console_parcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.UnPackedTruckDto;
import ru.liga.console_parcels.formatter.PrintResultFormatter;
import ru.liga.console_parcels.service.UnPackagingManager;
import ru.liga.console_parcels.service.UnPackagingService;

import java.util.List;

/**
 * Реализация менеджера для распаковки грузовиков.
 */
@Service
@Log4j2
public class DefaultUnPackagingManager implements UnPackagingManager {
    private UnPackagingService unPackagingService;
    private PrintResultFormatter printResultFormatter;

    /**
     * Конструктор с зависимостями.
     *
     * @param unPackagingService   Сервис для распаковки грузовиков.
     * @param printResultFormatter Форматировщик результатов распаковки.
     */
    @Autowired
    public DefaultUnPackagingManager(UnPackagingService unPackagingService, PrintResultFormatter printResultFormatter) {
        this.unPackagingService = unPackagingService;
        this.printResultFormatter = printResultFormatter;
    }

    /**
     * Распаковывает посылки из грузовиков и формирует результаты.
     *
     * @param truckFilePath       Путь к файлу с данными грузовиков.
     * @param parcelCountFilePath Путь к файлу с данными о количестве посылок.
     * @return Строка с результатами распаковки.
     */
    @Override
    public String unpackParcels(String truckFilePath) {
        log.info("Начало процесса распаковки");

        List<UnPackedTruckDto> unpackedTrucks = unPackagingService.unpackTruck(truckFilePath);
        log.info("Распаковка завершена. Распаковано {} машин", unpackedTrucks.size());

        log.info("Начало печати результатов распаковки для {} грузовиков", unpackedTrucks.size());
        StringBuilder unPackagingResult = printResultFormatter.transferUnpackingResultsToConsole(unpackedTrucks);

        return unPackagingResult.toString();
    }
}
