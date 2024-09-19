package ConsoleParcelsApp;

import ConsoleParcelsApp.controller.CargoManagementController;
import ConsoleParcelsApp.factory.impl.DelimeterFactoryImpl;
import ConsoleParcelsApp.factory.impl.PackagingServiceFactoryImpl;
import ConsoleParcelsApp.service.impl.*;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Scanner;

@Log4j2
public class ConsoleParcelsApplication {
    private static CargoManagementController cargoManagementController = new CargoManagementController(
            new ReceivingUserRequestServiceImpl(new Scanner(System.in)),
            new UserInteractionServiceImpl(new Scanner(System.in)),
            new PackagingSelectionServiceImpl(new PackagingServiceFactoryImpl()),
            new PrintResultServiceImpl(),
            new TruckToJsonWriterServiceImpl(),
            new UnPackagingServiceImpl(new DelimeterFactoryImpl())
    );

    public static void main(String[] args) throws IOException {
        log.info("Запуск приложения ConsoleParcelsApplication");
        cargoManagementController.handlePackagingOrUnpackingSelection();
    }
}
