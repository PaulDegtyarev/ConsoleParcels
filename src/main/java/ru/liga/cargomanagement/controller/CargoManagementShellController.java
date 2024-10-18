package ru.liga.cargomanagement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.cargomanagement.dto.PackRequestDto;
import ru.liga.cargomanagement.dto.enums.TruckPackageAlgorithm;
import ru.liga.cargomanagement.dto.UnpackedTruckDto;
import ru.liga.cargomanagement.entity.Truck;
import ru.liga.cargomanagement.formatter.ResultFormatter;
import ru.liga.cargomanagement.service.PackagingManager;
import ru.liga.cargomanagement.service.TruckParcelsUnpackingService;

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

        List<Truck> packedTrucks = packagingManager.pack(packRequestDto);

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