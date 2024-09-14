package ConsoleParcelsApp;

import ConsoleParcelsApp.controller.PackagingController;
import ConsoleParcelsApp.factory.impl.PackagingServiceFactoryImpl;
import ConsoleParcelsApp.service.impl.PrintTruckResultServiceImpl;
import ConsoleParcelsApp.service.impl.UserInputServiceImpl;

import java.util.Scanner;

public class ConsoleParcelsApplication {
    private static PackagingController packagingController = new PackagingController(
            new UserInputServiceImpl(new Scanner(System.in)),
            new PackagingServiceFactoryImpl(),
            new PrintTruckResultServiceImpl()
    );

    public static void main(String[] args) {
        packagingController.handleUserSelection();
    }
}
