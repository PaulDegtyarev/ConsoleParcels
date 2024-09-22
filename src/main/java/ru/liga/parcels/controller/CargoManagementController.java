package ru.liga.parcels.controller;

import ru.liga.parcels.dto.UnPackedTruckDto;
import ru.liga.parcels.exception.*;
import ru.liga.parcels.model.Parcel;
import ru.liga.parcels.model.Truck;
import lombok.extern.log4j.Log4j2;
import ru.liga.parcels.service.*;
import ru.liga.parcels.util.PackageReader;
import ru.liga.parcels.util.UserAlgorithmChoice;
import ru.liga.parcels.util.UserCommand;

import java.util.List;
import java.util.Optional;

@Log4j2
public class CargoManagementController {
    private ReceivingUserRequestService receivingUserRequestService;
    private UserInteractionService userInteractionService;
    private PackagingSelectionService packagingSelectionService;
    private PackageReader packageReader;
    private PrintResultService printResultService;
    private TruckToJsonWriterService truckToJsonWriterService;
    private UnPackagingService unPackagingService;

    public CargoManagementController(ReceivingUserRequestService receivingUserRequestService, UserInteractionService userInteractionService, PackagingSelectionService packagingSelectionService, PackageReader packageReader, PrintResultService printResultService, TruckToJsonWriterService truckToJsonWriterService, UnPackagingService unPackagingService) {
        this.receivingUserRequestService = receivingUserRequestService;
        this.userInteractionService = userInteractionService;
        this.packagingSelectionService = packagingSelectionService;
        this.packageReader = packageReader;
        this.printResultService = printResultService;
        this.truckToJsonWriterService = truckToJsonWriterService;
        this.unPackagingService = unPackagingService;
    }

    public void handlePackagingOrUnpackingSelection() {
        log.info("Начата обработка выбора упаковки или распаковки");
        boolean running = true;

        while (running) {
            UserCommand userCommand = receivingUserRequestService.requestUserChoice();
            log.debug("Получен выбор пользователя: {}", userCommand);

            switch (userCommand) {
                case PACK:
                    log.info("Пользователь выбрал упаковку");
                    Optional<List<Truck>> trucks = packTheTruck();
                    printResultService.printPackagingResults(trucks);
                    break;
                case UNPACK:
                    log.info("Пользователь выбрал распаковку");
                    Optional<List<UnPackedTruckDto>> unpackedTrucks = unpackTruck();
                    printResultService.printUnPackagingResults(unpackedTrucks);
                    break;
                case EXIT:
                    log.info("Пользователь выбрал выход из приложения");
                    running = false;
                    break;
                default:
                    log.warn("Некорректный параметр выбора пользователя: {}. Ожидается значение 1 - 3.", userCommand);
                    break;
            }
        }
    }

    private Optional<List<Truck>> packTheTruck() {
        log.info("Начало процесса упаковки");

        int numberOfCars = userInteractionService.requestForNumberOfCars();
        log.debug("Количество машин для упаковки: {}", numberOfCars);

        String inputFilePath = userInteractionService.requestForInputFilePath();
        log.debug("Путь к файлу с посылками: {}", inputFilePath);

        UserAlgorithmChoice algorithmChoice = userInteractionService.requestForAlgorithmChoice();
        log.debug("Выбранный алгоритм упаковки: {}", algorithmChoice);

        String filePathToWrite = userInteractionService.requestForFilePathToWrite();

        log.info("Начинается упаковка {} машин из файла {}", numberOfCars, inputFilePath);
        PackagingService packagingService = packagingSelectionService.selectPackagingService(algorithmChoice);
        log.debug("Выбран сервис для упаковки: {}", packagingService);


        List<Truck> trucks = null;
        try {
            log.info("Начало упаковки: файл = {}, количество автомобилей = {}", inputFilePath, numberOfCars);
            List<Parcel> parcels = packageReader.readPackages(inputFilePath);
            log.debug("Прочитано {} посылок из файла {}", parcels.size(), inputFilePath);

            trucks = packagingService.packPackages(parcels, numberOfCars);
            log.info("Упаковка завершена. Упаковано {} грузовиков", trucks.size());

            truckToJsonWriterService.writeTruckToJson(trucks, filePathToWrite);
            log.info("Запись результатов упаковки в JSON завершена");
        } catch (PackingException | FileWriteException | PackageShapeException |
                 FileNotFoundException packingException) {
            log.error(packingException.getMessage());
        }

        return Optional.ofNullable(trucks);
    }

    private Optional<List<UnPackedTruckDto>> unpackTruck() {
        log.info("Начало процесса распаковки");

        String filePathToUnpack = userInteractionService.requestForInputFilePath();
        log.debug("Путь к файлу с данными для распаковки: {}", filePathToUnpack);

        List<UnPackedTruckDto> unpackedTrucks = null;

        try {
            unpackedTrucks = unPackagingService.unpackTruck(filePathToUnpack);
            log.info("Распаковка завершена. Распаковано {} машин", unpackedTrucks.size());
        } catch (FileReadException fileReadException) {
            log.error("Ошибка при чтении файла {}: {}", filePathToUnpack, fileReadException.getMessage());
        }

        return Optional.ofNullable(unpackedTrucks);
    }
}