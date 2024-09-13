package ConsoleParcelsApp.controller;

import ConsoleParcelsApp.factory.PackagingServiceFactory;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.service.PrintTruckResultService;

import java.util.List;
import java.util.Scanner;

public class PackagingController {
    private Scanner scanner = new Scanner(System.in);
    private PackagingServiceFactory packagingServiceFactory;
    private PrintTruckResultService printTruckResultService;

    public PackagingController(PackagingServiceFactory packagingServiceFactory, PrintTruckResultService printTruckResultService) {
        this.packagingServiceFactory = packagingServiceFactory;
        this.printTruckResultService = printTruckResultService;
    }


    public void handleUserSelection() {
        System.out.print("Введите путь к файлу: ");

        String filePath = scanner.nextLine();

        System.out.print("Выберите алгоритм:\n1 - занимает максимальное пространство грузовика\n2 - один грузовик на одну посылку\n");

        int algorithmChoice = scanner.nextInt();
        scanner.nextLine();

        PackagingService packagingService = switch (algorithmChoice) {
            case 1 -> packagingServiceFactory.createOptimizedPackagingService();
            case 2 -> packagingServiceFactory.createSinglePackagingService();
            default -> throw new IllegalArgumentException("Неверный выбор алгоритма");
        };

        List<Truck> trucks = packagingService.packPackages(filePath);
        printTruckResultService.printResults(trucks);
        handleUserSelection();
    }
}
