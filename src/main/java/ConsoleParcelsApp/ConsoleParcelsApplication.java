package ConsoleParcelsApp;

import ConsoleParcelsApp.controller.CargoManagementController;
import ConsoleParcelsApp.factory.impl.PackagingServiceFactoryImpl;
import ConsoleParcelsApp.service.impl.*;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleParcelsApplication {
    private static CargoManagementController cargoManagementController = new CargoManagementController(
            new ReceivingUserRequestServiceImpl(new Scanner(System.in)),
            new PackagingInputServiceImpl(new Scanner(System.in)),
            new PackagingSelectionServiceImpl(new PackagingServiceFactoryImpl()),
            new PrintTruckResultServiceImpl(),
            new TruckToJsonWriterServiceImpl(),
            new UnPackagingServiceImpl()
    );

    public static void main(String[] args) throws IOException {
        cargoManagementController.handlePackagingOrUnpackingSelection();
    }
}
