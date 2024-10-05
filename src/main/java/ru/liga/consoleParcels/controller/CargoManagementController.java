package ru.liga.consoleParcels.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.consoleParcels.dto.PackRequestDto;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;
import ru.liga.consoleParcels.service.PackagingManager;
import ru.liga.consoleParcels.service.UnPackagingManager;

@Log4j2
@ShellComponent
public class CargoManagementController {
    private PackagingManager packagingManager;
    private UnPackagingManager unPackagingManager;

    @Autowired
    public CargoManagementController(PackagingManager packagingManager, UnPackagingManager unPackagingManager) {
        this.packagingManager = packagingManager;
        this.unPackagingManager = unPackagingManager;
    }

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

    @ShellMethod
    public String unpack(String truckFilePath, String parcelCountFilePath) {
        log.info("Пользователь выбрал распаковку");

        return unPackagingManager.unpackParcels(truckFilePath, parcelCountFilePath);
    }
}