package ru.liga.console_parcels.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.console_parcels.dto.PackRequestDto;
import ru.liga.console_parcels.dto.TruckPackageAlgorithm;
import ru.liga.console_parcels.dto.UnpackedTruckDto;
import ru.liga.console_parcels.formatter.ResultFormatter;
import ru.liga.console_parcels.service.PackagingManager;
import ru.liga.console_parcels.service.TruckParcelsUnpackingService;

import java.util.List;

/**
 * Контроллер для управления процессами упаковки и распаковки грузов.
 *
 * <p>
 * Этот контроллер отвечает за управление операциями по упаковке и распаковке грузов
 * на основе выбора пользователя и предоставленных файлов. Он использует сервисы
 * {@link PackagingManager} для упаковки и {@link TruckParcelsUnpackingService} для распаковки.
 * </p>
 *
 * <p>
 * Контроллер интегрирован с Spring Shell для взаимодействия через командную строку.
 * </p>
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
     * <p>
     * Этот метод обрабатывает запрос на упаковку посылок в грузовики на основе
     * предоставленных данных и выбора алгоритма пользователем.
     * </p>
     *
     * @param trucks          Строка, представляющая информацию о грузовиках.
     * @param inputFilePath   Путь к файлу с информацией о посылках.
     * @param algorithmChoice Выбор алгоритма пользователя для упаковки.
     * @param filePathToWrite Путь к файлу, где будет записан результат.
     * @return Строка с результатом операции упаковки.
     */
    @ShellMethod
    public String pack(String trucks, String inputFilePath, TruckPackageAlgorithm algorithmChoice, String filePathToWrite) {
        log.info("Пользователь выбрал упаковку.");

        PackRequestDto packRequestDto = new PackRequestDto(
                trucks,
                inputFilePath,
                algorithmChoice,
                filePathToWrite
        );

        return packagingManager.packParcels(packRequestDto);
    }

    /**
     * Метод для распаковки посылок из грузовиков.
     *
     * <p>
     * Этот метод обрабатывает запрос на распаковку посылок из грузовиков на основе
     * предоставленных файлов.
     * </p>
     *
     * @param truckFilePath       Путь к файлу с информацией о грузовиках.
     * @return Строка с результатом операции распаковки.
     */
    @ShellMethod
    public String unpack(String truckFilePath) {
        log.info("Пользователь выбрал распаковку");

        List<UnpackedTruckDto> unpackedTruck = truckParcelsUnpackingService.unpack(truckFilePath);

        return resultFormatter.convertUnpackingResultsToString(unpackedTruck);
    }
}