package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.formatter.PrintResultFormatter;
import ru.liga.consoleParcels.service.UnPackagingManager;
import ru.liga.consoleParcels.service.UnPackagingService;

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
    public String unpackParcels(String truckFilePath, String parcelCountFilePath) {
        log.info("Начало процесса распаковки");

        List<UnPackedTruckDto> unpackedTrucks = unPackagingService.unpackTruck(truckFilePath, parcelCountFilePath);
        log.info("Распаковка завершена. Распаковано {} машин", unpackedTrucks.size());

        log.info("Начало печати результатов распаковки для {} грузовиков", unpackedTrucks.size());
        StringBuilder unPackagingResult = printResultFormatter.transferUnpackingResultsToConsole(unpackedTrucks);

        return unPackagingResult.toString();
    }
}
