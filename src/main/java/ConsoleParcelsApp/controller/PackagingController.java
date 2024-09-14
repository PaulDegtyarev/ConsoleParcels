package ConsoleParcelsApp.controller;

import ConsoleParcelsApp.exception.WrongInputForNumberCarException;
import ConsoleParcelsApp.factory.PackagingServiceFactory;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.service.PrintTruckResultService;
import ConsoleParcelsApp.service.UserInputService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Scanner;

@Log4j2
public class PackagingController {
    private Scanner scanner = new Scanner(System.in);
    private UserInputService userInputService;
    private PackagingServiceFactory packagingServiceFactory;
    private PrintTruckResultService printTruckResultService;

    public PackagingController(UserInputService userInputService, PackagingServiceFactory packagingServiceFactory, PrintTruckResultService printTruckResultService) {
        this.userInputService = userInputService;
        this.packagingServiceFactory = packagingServiceFactory;
        this.printTruckResultService = printTruckResultService;
    }

    public void handleUserSelection() {
        int numberOfCars = 0;

        while (numberOfCars <= 0){
            try {
                numberOfCars = userInputService.requestForNumberOfCars();
            } catch (WrongInputForNumberCarException wrongInputForNumberCarException) {
                log.error(wrongInputForNumberCarException.getMessage());
            }
        }

        System.out.println("Введите путь к файлу: ");

        String filePath = scanner.nextLine();

        System.out.println("""
        Выберите алгоритм:
        1 - занимает максимальное пространство грузовика
        2 - один грузовик на одну посылку
        3 - равномерная погрузка посылок по грузовикам
        """);

        int algorithmChoice = scanner.nextInt();
        scanner.nextLine();

        PackagingService packagingService = switch (algorithmChoice) {
            case 1 -> packagingServiceFactory.createOptimizedPackagingService();
            case 2 -> packagingServiceFactory.createSinglePackagingService();
            default -> {
                log.error("Неверный выбор алгоритма");
                throw new IllegalArgumentException("Неверный выбор алгоритма");
            }
        };

        List<Truck> trucks = packagingService.packPackages(filePath, numberOfCars);
        printTruckResultService.printResults(trucks);
        handleUserSelection();
    }
}
