package ConsoleParcelsApp.controller;

import ConsoleParcelsApp.dto.UnPackedTruckDto;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.*;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;

@Log4j2
public class CargoManagementController {
    private ReceivingUserRequestService receivingUserRequestService;
    private UserInteractionService userInteractionService;
    private PackagingSelectionService packagingSelectionService;
    private PrintResultService printResultService;
    private TruckToJsonWriterService truckToJsonWriterService;
    private UnPackagingService unPackagingService;

    public CargoManagementController(ReceivingUserRequestService receivingUserRequestService, UserInteractionService userInteractionService, PackagingSelectionService packagingSelectionService, PrintResultService printResultService, TruckToJsonWriterService truckToJsonWriterService, UnPackagingService unPackagingService) {
        this.receivingUserRequestService = receivingUserRequestService;
        this.userInteractionService = userInteractionService;
        this.packagingSelectionService = packagingSelectionService;
        this.printResultService = printResultService;
        this.truckToJsonWriterService = truckToJsonWriterService;
        this.unPackagingService = unPackagingService;
    }

    public void handlePackagingOrUnpackingSelection() throws IOException {
        log.info("Начата обработка выбора упаковки или распаковки");

        int userChoice = receivingUserRequestService.requestUserChoice();

        log.debug("Получен выбор пользователя: {}", userChoice);

        switch (userChoice) {
            case 1:
                log.info("Пользователь выбрал упаковку");
                packTheTruck();
                handlePackagingOrUnpackingSelection();
                break;
            case 2:
                log.info("Пользователь выбрал распаковку");
                unpackTruck();
                handlePackagingOrUnpackingSelection();
                break;
            default:
                log.warn("Некорректный параметр выбора пользователя: {}. Ожидается значение 1 или 2.", userChoice);
                handlePackagingOrUnpackingSelection();
        }
    }

    private void packTheTruck() {
        log.info("Начало процесса упаковки");

        int numberOfCars = userInteractionService.requestForNumberOfCars();

        log.debug("Количество машин для упаковки: {}", numberOfCars);

        String filePath = userInteractionService.requestForFilePath();

        log.debug("Путь к файлу с посылками: {}", filePath);

        int algorithmChoice = userInteractionService.requestForAlgorithmChoice();

        log.debug("Выбранный алгоритм упаковки: {}", algorithmChoice);

        log.info("Начинается упаковка {} машин из файла {}", numberOfCars, filePath);

        PackagingService packagingService = packagingSelectionService.selectPackagingService(algorithmChoice);

        log.debug("Выбран сервис для упаковки: {}", packagingService);

        try {
            List<Truck> trucks = packagingService.packPackages(filePath, numberOfCars);

            log.info("Упаковка завершена. Упаковано {} грузовиков", trucks.size());

            printResultService.printPackagingResults(trucks);

            log.debug("Результаты упаковки напечатаны");

            truckToJsonWriterService.writeTruckToJson(trucks);

            log.info("Запись результатов упаковки в JSON завершена");

        } catch (RuntimeException runtimeException) {
            log.error(runtimeException.getMessage());
        }
    }

    private void unpackTruck() throws IOException {
        log.info("Начало процесса распаковки");

        String filePath = userInteractionService.requestForFilePath();

        log.debug("Путь к файлу с данными для распаковки: {}", filePath);

        try {
            List<UnPackedTruckDto> unpackedTrucks = unPackagingService.unpackTruck(filePath);

            log.info("Распаковка завершена. Распаковано {} машин", unpackedTrucks.size());

            printResultService.printUnPackagingResults(unpackedTrucks);

            log.debug("Результаты распаковки напечатаны");
        } catch (IOException ioException) {
            log.error("Ошибка при чтении файла {}: {}", filePath, ioException.getMessage());
            unpackTruck();
        }
    }
}