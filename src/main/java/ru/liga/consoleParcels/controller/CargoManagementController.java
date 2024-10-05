package ru.liga.consoleParcels.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CargoManagementController {
    private PackagingManager packagingManager;
    private UnPackagingManager unPackagingManager;

    /**
     * Конструктор класса CargoManagementController.
     *
     * @param packagingManager   Менеджер для управления процессом упаковки.
     * @param unPackagingManager Менеджер для управления процессом распаковки.
     */
    @Autowired
    public CargoManagementController(PackagingManager packagingManager, UnPackagingManager unPackagingManager) {
        this.packagingManager = packagingManager;
        this.unPackagingManager = unPackagingManager;
    }

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
     * @param truckFilePath       Путь к файлу с информацией о грузовиках.
     * @param parcelCountFilePath Путь к файлу с информацией о количестве посылок.
     * @return Строка с результатом операции распаковки.
     */
    @ShellMethod
    public String unpack(String truckFilePath, String parcelCountFilePath) {
        log.info("Пользователь выбрал распаковку");

        return unPackagingManager.unpackParcels(truckFilePath, parcelCountFilePath);
    }
}