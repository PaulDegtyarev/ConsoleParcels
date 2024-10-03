package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.exception.FileReadException;
import ru.liga.consoleParcels.formatter.PrintResultFormatter;
import ru.liga.consoleParcels.service.UnPackagingManager;
import ru.liga.consoleParcels.service.UnPackagingService;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class DefaultUnPackagingManager implements UnPackagingManager {
    private UnPackagingService unPackagingService;
    private PrintResultFormatter printResultFormatter;

    @Autowired
    public DefaultUnPackagingManager(UnPackagingService unPackagingService, PrintResultFormatter printResultFormatter) {
        this.unPackagingService = unPackagingService;
        this.printResultFormatter = printResultFormatter;
    }

    @Override
    public String unpackParcels(String filePathToUnpack) {
        log.info("Начало процесса распаковки");
        log.debug("Путь к файлу с данными для распаковки: {}", filePathToUnpack);

        List<UnPackedTruckDto> unpackedTrucks = new ArrayList<>();
        try {
            unpackedTrucks = unPackagingService.unpackTruck(filePathToUnpack);
            log.info("Распаковка завершена. Распаковано {} машин", unpackedTrucks.size());
        } catch (FileReadException fileReadException) {
            log.error("Ошибка при чтении файла {}: {}", filePathToUnpack, fileReadException.getMessage());
        }

        log.info("Начало печати результатов распаковки для {} грузовиков", unpackedTrucks.size());
        StringBuilder unPackagingResult = printResultFormatter.transferUnpackingResultsToConsole(unpackedTrucks);

        return unPackagingResult.toString();
    }
}
