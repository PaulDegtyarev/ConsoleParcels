package ru.liga.consoleParcels.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.consoleParcels.dto.PackagingParametersDto;
import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.exception.*;
import ru.liga.consoleParcels.formatter.PrintResultFormatter;
import ru.liga.consoleParcels.mapper.ParcelMapper;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер, отвечающий за управление логикой упаковки и распаковки грузов.
 * <p>
 * Контроллер обрабатывает выбор пользователя между упаковкой и распаковкой,
 * запрашивает необходимые параметры, вызывает соответствующие сервисы
 * и выводит результаты на консоль.
 */
@Log4j2
@ShellComponent
public class CargoManagementController {
    private ReceivingUserRequestService receivingUserRequestService;
    private PackagingSelectionService packagingSelectionService;
    private TruckToJsonWriterService truckToJsonWriterService;
    private UnPackagingService unPackagingService;
    private PrintResultFormatter printResultFormatter;
    private PackageReader packageReader;

    /**
     * Конструктор контроллера.
     *
     * @param receivingUserRequestService Сервис для получения запросов от пользователя.
     * @param packagingSelectionService   Сервис для выбора сервиса упаковки.
     * @param truckToJsonWriterService    Сервис для записи данных о грузовиках в JSON.
     * @param unPackagingService          Сервис для распаковки грузовиков.
     * @param printResultFormatter        Сервис для форматирования результатов упаковки/распаковки.
     */
    @Autowired
    public CargoManagementController(ReceivingUserRequestService receivingUserRequestService, PackagingSelectionService packagingSelectionService, TruckToJsonWriterService truckToJsonWriterService, UnPackagingService unPackagingService, PrintResultFormatter printResultFormatter, PackageReader packageReader) {
        this.receivingUserRequestService = receivingUserRequestService;
        this.packagingSelectionService = packagingSelectionService;
        this.truckToJsonWriterService = truckToJsonWriterService;
        this.unPackagingService = unPackagingService;
        this.printResultFormatter = printResultFormatter;
        this.packageReader = packageReader;
    }

    @ShellMethod
    public void pack() {
        log.info("Пользователь выбрал упаковку");
        log.info("Начало процесса упаковки");

        PackagingParametersDto packagingParameters = receivingUserRequestService.requestParametersForPacking();
        log.debug("Количество машин для упаковки: {}", packagingParameters.getNumberOfCars());
        log.info("Начало упаковки: файл = {}, количество автомобилей = {}", packagingParameters.getInputFilePath(), packagingParameters.getNumberOfCars());
        log.debug("Выбранный алгоритм упаковки: {}", packagingParameters.getAlgorithmChoice());

        PackagingService packagingService = packagingSelectionService.selectPackagingService(packagingParameters.getAlgorithmChoice());
        log.debug("Выбран сервис для упаковки: {}", packagingService);

        List<Truck> trucks = new ArrayList<>();
        try {
            List<ParcelMapper> parcels = packageReader.readPackages(packagingParameters.getInputFilePath());
            log.debug("Прочитано {} посылок из файла {}", parcels.size(), packagingParameters.getInputFilePath());

            log.info("Начинается упаковка {} машин из файла {}", packagingParameters.getNumberOfCars(), packagingParameters.getInputFilePath());
            trucks = packagingService.packPackages(parcels, packagingParameters.getNumberOfCars());
            log.info("Упаковка завершена. Упаковано {} грузовиков", trucks.size());

            truckToJsonWriterService.writeTruckToJson(trucks, packagingParameters.getFilePathToWrite());
            log.info("Запись результатов упаковки в JSON завершена");
        } catch (PackingException | FileWriteException | PackageShapeException |
                 FileNotFoundException packingException) {
            log.error(packingException.getMessage());
        }

        log.info("Начало печати результатов упаковки для {} грузовиков", trucks.size());

        StringBuilder packagingResult = printResultFormatter.transferPackagingResultsToConsole(trucks);

        System.out.println(packagingResult);
        log.info("Завершение печати результатов упаковки");
    }

    @ShellMethod
    public void unpack() {
        log.info("Пользователь выбрал распаковку");
        log.info("Начало процесса распаковки");

        String filePathToUnpack = receivingUserRequestService.requestForFilePathToUnpackTruck();
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

        System.out.println(unPackagingResult);
        log.info("Завершение печати результатов распаковки");
    }
}