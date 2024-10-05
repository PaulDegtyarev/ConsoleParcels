package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.PackRequestDto;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.exception.ParcelNotFoundException;
import ru.liga.consoleParcels.formatter.PrintResultFormatter;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.repository.ParcelRepository;
import ru.liga.consoleParcels.service.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация менеджера упаковки посылок.
 */
@Service
@Log4j2
public class DefaultPackagingManager implements PackagingManager {
    private PackagingSelectionService packagingSelectionService;
    private TruckToJsonWriterService truckToJsonWriterService;
    private PrintResultFormatter printResultFormatter;
    private PackageReader packageReader;
    private ParcelRepository parcelRepository;

    /**
     * Конструктор с зависимостями.
     *
     * @param packagingSelectionService Сервис выбора алгоритма упаковки.
     * @param truckToJsonWriterService  Сервис записи данных о грузовиках в JSON.
     * @param printResultFormatter      Форматировщик результатов упаковки.
     * @param packageReader             Чтение посылок из файла.
     * @param parcelRepository          Репозиторий посылок.
     */
    @Autowired
    public DefaultPackagingManager(PackagingSelectionService packagingSelectionService, TruckToJsonWriterService truckToJsonWriterService, PrintResultFormatter printResultFormatter, PackageReader packageReader, ParcelRepository parcelRepository) {
        this.packagingSelectionService = packagingSelectionService;
        this.truckToJsonWriterService = truckToJsonWriterService;
        this.printResultFormatter = printResultFormatter;
        this.packageReader = packageReader;
        this.parcelRepository = parcelRepository;
    }

    /**
     * Упаковывает посылки согласно запросу.
     *
     * @param packRequestDto Запрос на упаковку.
     * @return Строка с результатами упаковки.
     */
    @Override
    public String packParcels(PackRequestDto packRequestDto) {
        log.info("Начало процесса упаковки");

        PackagingService packagingService = packagingSelectionService.selectPackagingService(packRequestDto.getAlgorithmChoice());
        log.debug("Выбран сервис для упаковки: {}", packagingService.getClass().getSimpleName());

        List<ParcelForPackagingDto> parcelsForPackaging;
        try {
            String[] inputDataElements = packRequestDto.getInputData().split(",");
            List<String> parcelNames = Arrays.stream(inputDataElements)
                    .map(String::trim)
                    .toList();

            parcelsForPackaging = parcelNames.stream()
                    .map(parcelName -> {
                        Parcel parcel = parcelRepository.findParcelByName(parcelName.trim().toLowerCase())
                                .orElseThrow(() -> new ParcelNotFoundException("Посылка с именем " + parcelName + " не найдена."));
                        return new ParcelForPackagingDto(
                                parcel.getShape().length,
                                parcel.getShape()[0].length,
                                parcel.getShape()
                        );
                    })
                    .collect(Collectors.toList());
            log.info("Получено {} посылок из репозитория", parcelsForPackaging.size());
        } catch (ParcelNotFoundException parcelNotFoundException) {
            parcelsForPackaging = packageReader.readPackages(packRequestDto.getInputData());
            log.debug("Прочитано {} посылок из файла {}", parcelsForPackaging.size(), packRequestDto.getInputData());
        }

        List<Truck> trucks;

        log.info("Начинается упаковка {} машин из файла {}", packRequestDto.getTrucks(), packRequestDto.getInputData());
        trucks = packagingService.packPackages(parcelsForPackaging, packRequestDto.getTrucks());
        log.info("Упаковка завершена. Упаковано {} грузовиков", trucks.size());

        truckToJsonWriterService.writeTruckToJson(trucks, packRequestDto.getFilePathToWrite());
        log.info("Запись результатов упаковки в JSON завершена");

        log.info("Начало печати результатов упаковки для {} грузовиков", trucks.size());
        return printResultFormatter.transferPackagingResultsToConsole(trucks).toString();
    }
}
