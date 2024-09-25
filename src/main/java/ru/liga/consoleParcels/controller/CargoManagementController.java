package ru.liga.consoleParcels.controller;

import ru.liga.consoleParcels.dto.PackagingParametersDto;
import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.exception.*;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.model.Truck;
import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.service.*;
import ru.liga.consoleParcels.service.impl.PackageReader;
import ru.liga.consoleParcels.model.UserCommand;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CargoManagementController {
    private ReceivingUserRequestService receivingUserRequestService;
    private PackagingSelectionService packagingSelectionService;
    private PackageReader packageReader;
    private PrintResultService printResultService;
    private TruckToJsonWriterService truckToJsonWriterService;
    private UnPackagingService unPackagingService;

    public CargoManagementController(ReceivingUserRequestService receivingUserRequestService, PackagingSelectionService packagingSelectionService, PackageReader packageReader, PrintResultService printResultService, TruckToJsonWriterService truckToJsonWriterService, UnPackagingService unPackagingService) {
        this.receivingUserRequestService = receivingUserRequestService;
        this.packagingSelectionService = packagingSelectionService;
        this.packageReader = packageReader;
        this.printResultService = printResultService;
        this.truckToJsonWriterService = truckToJsonWriterService;
        this.unPackagingService = unPackagingService;
    }

    public void handlePackagingOrUnpackingSelection() {
        log.info("Начата обработка выбора упаковки или распаковки");
        boolean isRunning = true;

        while (isRunning) {
            UserCommand userCommand = receivingUserRequestService.requestUserChoice();
            log.debug("Получен выбор пользователя: {}", userCommand);

            switch (userCommand) {
                case PACK:
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
                        List<Parcel> parcels = packageReader.readPackages(packagingParameters.getInputFilePath());
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

                    printResultService.printPackagingResults(trucks);
                    break;
                case UNPACK:
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

                    printResultService.printUnPackagingResults(unpackedTrucks);
                    break;
                case EXIT:
                    log.info("Пользователь выбрал выход из приложения");
                    isRunning = false;
                    break;
                default:
                    log.warn("Некорректный параметр выбора пользователя: {}. Ожидается значение 1 - 3.", userCommand);
                    break;
            }
        }
    }
}