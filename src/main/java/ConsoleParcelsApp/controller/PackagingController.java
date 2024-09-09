package ConsoleParcelsApp.controller;

import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.service.impl.OptimizedPackagingServiceImpl;
import ConsoleParcelsApp.service.impl.SinglePackagingServiceImpl;

import java.io.IOException;
import java.util.Scanner;

public class PackagingController {
    private Scanner scanner = new Scanner(System.in);
    private PackagingService packagingService;

    public void getUserRequest() {
        System.out.print("Введите путь к файлу: ");

        String filePath = scanner.nextLine();

        System.out.print("Выберите алгоритм (1 - обычный, 2 - один грузовик на одну посылку): ");

        int algorithmChoice = scanner.nextInt();
        scanner.nextLine();

        if (algorithmChoice == 1) {
            packagingService = new OptimizedPackagingServiceImpl();
        }

        else if (algorithmChoice == 2) {
            packagingService = new SinglePackagingServiceImpl();
        }

        else {
            System.out.println("Неверный выбор");
        }

        try {
            packagingService.packPackages(filePath);
            packagingService.printResults();
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        }
    }
}
