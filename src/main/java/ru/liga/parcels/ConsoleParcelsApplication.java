package ru.liga.parcels;

import ru.liga.parcels.controller.CargoManagementController;
import ru.liga.parcels.factory.impl.DelimeterFactoryImpl;
import ru.liga.parcels.factory.impl.PackagingServiceFactoryImpl;
import lombok.extern.log4j.Log4j2;
import ru.liga.parcels.service.impl.*;
import ru.liga.parcels.util.PackageReader;

import java.util.Scanner;

@Log4j2
public class ConsoleParcelsApplication {

    public static void main(String[] args) {
        log.info("Запуск приложения ConsoleParcelsApplication");

        Scanner scanner = new Scanner(System.in);
        new CargoManagementController(
                new ReceivingUserRequestServiceImpl(
                        scanner
                ),
                new UserInteractionServiceImpl(
                        scanner
                ),
                new PackagingSelectionServiceImpl(
                        new PackagingServiceFactoryImpl()
                ),
                new PackageReader(),
                new PrintResultServiceImpl(),
                new TruckToJsonWriterServiceImpl(),
                new UnPackagingServiceImpl(
                        new DelimeterFactoryImpl()
                )
        ).handlePackagingOrUnpackingSelection();
    }
}
