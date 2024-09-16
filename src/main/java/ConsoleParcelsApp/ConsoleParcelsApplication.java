package ConsoleParcelsApp;

import ConsoleParcelsApp.controller.PackagingController;
import ConsoleParcelsApp.factory.impl.PackagingServiceFactoryImpl;
import ConsoleParcelsApp.service.impl.PackagingSelectionServiceImpl;
import ConsoleParcelsApp.service.impl.PrintTruckResultServiceImpl;
import ConsoleParcelsApp.service.impl.TruckToJsonWriterServiceImpl;
import ConsoleParcelsApp.service.impl.UserInputServiceImpl;

import java.util.Scanner;

public class ConsoleParcelsApplication {
    private static PackagingController packagingController = new PackagingController(
            new UserInputServiceImpl(new Scanner(System.in)),
            new PackagingSelectionServiceImpl(new PackagingServiceFactoryImpl()),
            new PrintTruckResultServiceImpl(),
            new TruckToJsonWriterServiceImpl()
    );

    public static void main(String[] args) {
        packagingController.handleUserSelection();
    }
}
