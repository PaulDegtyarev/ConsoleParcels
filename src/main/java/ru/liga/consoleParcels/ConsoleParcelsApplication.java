package ru.liga.consoleParcels;

import ru.liga.consoleParcels.builder.impl.PackagingCommandBuilderImpl;
import ru.liga.consoleParcels.controller.CargoManagementController;
import ru.liga.consoleParcels.factory.impl.DelimeterFactoryImpl;
import ru.liga.consoleParcels.factory.impl.PackagingServiceFactoryImpl;
import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.formatter.impl.DefaultPrintResultFormatter;
import ru.liga.consoleParcels.service.impl.*;
import ru.liga.consoleParcels.service.impl.PackageReader;

import java.util.Scanner;

@Log4j2
public class ConsoleParcelsApplication {

    public static void main(String[] args) {
        log.info("Запуск приложения ConsoleParcelsApplication");


        new CargoManagementController(
                new ReceivingUserRequestServiceImpl(
                        new Scanner(System.in),
                        new PackagingCommandBuilderImpl(
                                new Scanner(System.in)
                        )
                ),
                new PackagingSelectionServiceImpl(
                        new PackagingServiceFactoryImpl()
                ),
                new PackageReader(),
                new TruckToJsonWriterServiceImpl(),
                new UnPackagingServiceImpl(
                        new DelimeterFactoryImpl()
                ),
                new DefaultPrintResultFormatter()
        ).handlePackagingOrUnpackingSelection();
    }
}
