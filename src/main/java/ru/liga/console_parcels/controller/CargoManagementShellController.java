package ru.liga.console_parcels.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.console_parcels.dto.PackRequestDto;
import ru.liga.console_parcels.dto.TruckPackageAlgorithm;
import ru.liga.console_parcels.dto.UnpackedTruckDto;
import ru.liga.console_parcels.entity.Truck;
import ru.liga.console_parcels.formatter.ResultFormatter;
import ru.liga.console_parcels.service.PackagingManager;
import ru.liga.console_parcels.service.TruckParcelsUnpackingService;

import java.util.List;

/**
 * Контроллер для управления процессами упаковки и распаковки грузов через консоль.
 */
@Log4j2
@ShellComponent
@RequiredArgsConstructor
public class CargoManagementShellController {
    private final PackagingManager packagingManager;
    private final TruckParcelsUnpackingService truckParcelsUnpackingService;
    private final ResultFormatter resultFormatter;

    /**
     * Метод для упаковки посылок в грузовики.
     *
     * @param trucks           Строка, представляющая информацию о грузовиках.
     * @param inputFilePath    Путь к файлу в формате .txt с информацией о посылках.
     * @param packageAlgorithm Выбор алгоритма пользователя для упаковки.
     * @param filePathToWrite  Путь к файлу в формате .json, где будет записан результат.
     * @return Строка с результатом операции упаковки.
     */
    @ShellMethod
    public String pack(String trucks, String inputFilePath, TruckPackageAlgorithm packageAlgorithm, String filePathToWrite) {
        log.info("Пользователь выбрал упаковку.");

        PackRequestDto packRequestDto = new PackRequestDto(
                trucks,
                inputFilePath,
                packageAlgorithm,
                filePathToWrite
        );

        List<Truck> packedTrucks = packagingManager.packParcels(packRequestDto);

        return resultFormatter.convertPackagingResultsToString(packedTrucks);
    }

    /**
     * Метод для распаковки посылок из грузовиков.
     *
     * @param truckFilePath Путь к файлу в формате .json с информацией о грузовиках для распаковки.
     * @return Строка с результатом операции распаковки.
     */
    @ShellMethod
    public String unpack(String truckFilePath) {
        log.info("Пользователь выбрал распаковку");

        List<UnpackedTruckDto> unpackedTruck = truckParcelsUnpackingService.unpack(truckFilePath);

        return resultFormatter.convertUnpackingResultsToString(unpackedTruck);
    }
}