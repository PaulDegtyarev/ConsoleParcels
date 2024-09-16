package ConsoleParcelsApp.controller;

import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.*;
import ConsoleParcelsApp.util.PackageReader;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Log4j2
public class CargoManagementController {
    private ReceivingUserRequestService receivingUserRequestService;
    private PackagingInputService packagingInputService;
    private PackagingSelectionService packagingSelectionService;
    private PrintTruckResultService printTruckResultService;
    private TruckToJsonWriterService truckToJsonWriterService;
    private UnPackagingService unPackagingService;

    public CargoManagementController(ReceivingUserRequestService receivingUserRequestService, PackagingInputService packagingInputService, PackagingSelectionService packagingSelectionService, PrintTruckResultService printTruckResultService, TruckToJsonWriterService truckToJsonWriterService, UnPackagingService unPackagingService) {
        this.receivingUserRequestService = receivingUserRequestService;
        this.packagingInputService = packagingInputService;
        this.packagingSelectionService = packagingSelectionService;
        this.printTruckResultService = printTruckResultService;
        this.truckToJsonWriterService = truckToJsonWriterService;
        this.unPackagingService = unPackagingService;
    }

    public void handlePackagingOrUnpackingSelection() throws IOException {
        int userChoice = receivingUserRequestService.requestUserChoice();

        switch (userChoice) {
            case 1:
                packTheTruck();
                handlePackagingOrUnpackingSelection();
                break;
            case 2:
                unpackTruck();
                handlePackagingOrUnpackingSelection();
                break;
            default:
                log.error("Некорректный параметр выбора пользователя. Ожидается значение 1 или 2.");
                handlePackagingOrUnpackingSelection();
        }


    }

    private void packTheTruck() {
        int numberOfCars = packagingInputService.requestForNumberOfCars();

        String filePath = packagingInputService.requestForFilePath();

        int algorithmChoice = packagingInputService.requestForAlgorithmChoice();

        PackagingService packagingService = packagingSelectionService.selectPackagingService(algorithmChoice);

        try {
            List<Truck> trucks = packagingService.packPackages(filePath, numberOfCars);

            printTruckResultService.printResults(trucks);

            truckToJsonWriterService.writeTruckToJson(trucks);

        } catch (RuntimeException runtimeException) {
            log.error(runtimeException.getMessage());
        }
    }

    private void unpackTruck() throws IOException {
        unPackagingService.unpackTruck();
    }
}
