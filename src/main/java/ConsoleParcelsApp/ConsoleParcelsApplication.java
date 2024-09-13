package ConsoleParcelsApp;

import ConsoleParcelsApp.controller.PackagingController;
import ConsoleParcelsApp.factory.impl.PackagingServiceFactoryImpl;
import ConsoleParcelsApp.service.impl.PrintTruckResultServiceImpl;

public class ConsoleParcelsApplication {
    private static PackagingController packagingController = new PackagingController(
            new PackagingServiceFactoryImpl(),
            new PrintTruckResultServiceImpl()
    );

    public static void main(String[] args) {
        packagingController.handleUserSelection();
    }
}
