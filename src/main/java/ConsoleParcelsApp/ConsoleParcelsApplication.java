package ConsoleParcelsApp;

import ConsoleParcelsApp.model.Parcel;
import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.service.impl.OptimizedPackagingServiceImpl;
import ConsoleParcelsApp.service.impl.SinglePackagingServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConsoleParcelsApplication {
    private static Scanner scanner = new Scanner(System.in);
    private static PackageReader packageReader = new PackageReader();
    private static PackagingService packagingService;


    public static void main(String[] args) {
        System.out.print("Введите путь к файлу: ");
        String filePath = scanner.nextLine();
    // /home/paul/IdeaProjects/ConsoleParcels/input-data.txt

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
            List<Parcel> parcels = packageReader.readPackages(filePath);
            packagingService.packPackages(parcels);
            packagingService.printResults();
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        }
    }
}
