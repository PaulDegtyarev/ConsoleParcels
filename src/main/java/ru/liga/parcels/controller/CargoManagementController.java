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
                    packTheTruck();
                    break;
                case UNPACK:
                    log.info("Пользователь выбрал распаковку");
                    unpackTruck();
                    break;
                case EXIT:
                    log.info("Пользователь выбрал выход из приложения");
                    running = false;
                default:
                    log.warn("Некорректный параметр выбора пользователя: {}. Ожидается значение 1 - 3.", userCommand);
                    break;
            }
        }
    }

    private void packTheTruck() {
        log.info("Начало процесса упаковки");

        int numberOfCars = userInteractionService.requestForNumberOfCars();
        log.debug("Количество машин для упаковки: {}", numberOfCars);

        String filePath = userInteractionService.requestForFilePath();
        log.debug("Путь к файлу с посылками: {}", filePath);

        UserAlgorithmChoice algorithmChoice = userInteractionService.requestForAlgorithmChoice();
        log.debug("Выбранный алгоритм упаковки: {}", algorithmChoice);

        log.info("Начинается упаковка {} машин из файла {}", numberOfCars, filePath);
        PackagingService packagingService = packagingSelectionService.selectPackagingService(algorithmChoice);
        log.debug("Выбран сервис для упаковки: {}", packagingService);

        try {
            log.info("Начало упаковки: файл = {}, количество автомобилей = {}", filePath, numberOfCars);
            List<Parcel> parcels = packageReader.readPackages(filePath);
            log.debug("Прочитано {} посылок из файла {}", parcels.size(), filePath);

            List<Truck> trucks = packagingService.packPackages(parcels, numberOfCars);
            log.info("Упаковка завершена. Упаковано {} грузовиков", trucks.size());

            printResultService.printPackagingResults(trucks);
            log.debug("Результаты упаковки напечатаны");

            truckToJsonWriterService.writeTruckToJson(trucks);
            log.info("Запись результатов упаковки в JSON завершена");

        } catch (PackingException | FileWriteException | PackageShapeException | FileNotFoundException packingException) {
            log.error(packingException.getMessage());
        }
    }

    private void unpackTruck() {
        log.info("Начало процесса распаковки");

        String filePath = userInteractionService.requestForFilePath();
        log.debug("Путь к файлу с данными для распаковки: {}", filePath);

        try {
            List<UnPackedTruckDto> unpackedTrucks = unPackagingService.unpackTruck(filePath);
            log.info("Распаковка завершена. Распаковано {} машин", unpackedTrucks.size());

            printResultService.printUnPackagingResults(unpackedTrucks);
            log.debug("Результаты распаковки напечатаны");
        } catch (FileReadException fileReadException) {
            log.error("Ошибка при чтении файла {}: {}", filePath, fileReadException.getMessage());
            unpackTruck();
        }
    }
}