package ConsoleParcelsApp.controller;

import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.*;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class PackagingController {
    private UserInputService userInputService;
    private PackagingSelectionService packagingSelectionService;
    private PrintTruckResultService printTruckResultService;
    private TruckToJsonWriterService truckToJsonWriterService;

    public PackagingController(UserInputService userInputService, PackagingSelectionService packagingSelectionService, PrintTruckResultService printTruckResultService, TruckToJsonWriterService truckToJsonWriterService) {
        this.userInputService = userInputService;
        this.packagingSelectionService = packagingSelectionService;
        this.printTruckResultService = printTruckResultService;
        this.truckToJsonWriterService = truckToJsonWriterService;
    }

    public void handleUserSelection() {
        int numberOfCars = userInputService.requestForNumberOfCars();

        String filePath = userInputService.requestForFilePath();

        int algorithmChoice = userInputService.requestForAlgorithmChoice();

        PackagingService packagingService = packagingSelectionService.selectPackagingService(algorithmChoice);

        try {
            List<Truck> trucks = packagingService.packPackages(filePath, numberOfCars);

            printTruckResultService.printResults(trucks);

            truckToJsonWriterService.writeTruckToJson(trucks);

            handleUserSelection();
        } catch (RuntimeException runtimeException) {
            log.error(runtimeException.getMessage());
            handleUserSelection();
        }
    }
}
