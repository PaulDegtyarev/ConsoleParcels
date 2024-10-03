package ru.liga.consoleParcels.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.consoleParcels.dto.PackRequest;
import ru.liga.consoleParcels.dto.PackagingParametersDto;
import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.exception.*;
import ru.liga.consoleParcels.formatter.PrintResultFormatter;
import ru.liga.consoleParcels.mapper.ParcelMapper;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;
import ru.liga.consoleParcels.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер, отвечающий за управление логикой упаковки и распаковки грузов.
 * <p>
 * Контроллер обрабатывает выбор пользователя между упаковкой и распаковкой,
 * запрашивает необходимые параметры, вызывает соответствующие сервисы
 * и выводит результаты на консоль.
 */
@Log4j2
@ShellComponent
public class CargoManagementController {
    private PackagingManager packagingManager;
    private UnPackagingService unPackagingService;

    @Autowired
    public CargoManagementController(PackagingManager packagingManager) {
        this.packagingManager = packagingManager;
    }

    @ShellMethod
    public String pack(String trucks, String inputFilePath, UserAlgorithmChoice algorithmChoice, String filePathToWrite) {
        log.info("Пользователь выбрал упаковку");

        PackRequest packRequest = new PackRequest(
                trucks,
                inputFilePath,
                algorithmChoice,
                filePathToWrite
        );

        return packagingManager.packParcels(packRequest);
    }

//    @ShellMethod
//    public void unpack() {
//        log.info("Пользователь выбрал распаковку");
//        log.info("Начало процесса распаковки");
//
//        String filePathToUnpack = receivingUserRequestService.requestForFilePathToUnpackTruck();
//        log.debug("Путь к файлу с данными для распаковки: {}", filePathToUnpack);
//
//        List<UnPackedTruckDto> unpackedTrucks = new ArrayList<>();
//        try {
//            unpackedTrucks = unPackagingService.unpackTruck(filePathToUnpack);
//            log.info("Распаковка завершена. Распаковано {} машин", unpackedTrucks.size());
//        } catch (FileReadException fileReadException) {
//            log.error("Ошибка при чтении файла {}: {}", filePathToUnpack, fileReadException.getMessage());
//        }
//
//        log.info("Начало печати результатов распаковки для {} грузовиков", unpackedTrucks.size());
//        StringBuilder unPackagingResult = printResultFormatter.transferUnpackingResultsToConsole(unpackedTrucks);
//
//        System.out.println(unPackagingResult);
//        log.info("Завершение печати результатов распаковки");
//    }
}