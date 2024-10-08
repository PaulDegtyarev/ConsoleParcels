package ru.liga.consoleParcels.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.consoleParcels.dto.PackRequestDto;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;
import ru.liga.consoleParcels.service.PackagingManager;
import ru.liga.consoleParcels.service.UnPackagingManager;

/**
 * Контроллер для управления процессами упаковки и распаковки грузов.
 *
 * <p>
 * Этот контроллер отвечает за управление операциями по упаковке и распаковке грузов
 * на основе выбора пользователя и предоставленных файлов. Он использует сервисы
 * {@link PackagingManager} для упаковки и {@link UnPackagingManager} для распаковки.
 * </p>
 *
 * <p>
 * Контроллер интегрирован с Spring Shell для взаимодействия через командную строку.
 * </p>
 */
@Log4j2
@ShellComponent
@RequiredArgsConstructor
public class CargoManagementController {
    private final PackagingManager packagingManager;
    private final UnPackagingManager unPackagingManager;

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
    public String pack(String trucks, String inputFilePath, UserAlgorithmChoice algorithmChoice, String filePathToWrite) {
        log.info("Пользователь выбрал упаковку");

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
     * @param truckFilePath Путь к файлу с информацией о грузовиках.
     * @return Строка с результатом операции распаковки.
     */
    @ShellMethod
    public String unpack(String truckFilePath) {
        log.info("Пользователь выбрал распаковку");

        return unPackagingManager.unpackParcels(truckFilePath);
    }
}