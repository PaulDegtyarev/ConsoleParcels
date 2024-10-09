package ru.liga.console_parcels.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.console_parcels.dto.PackRequestDto;
import ru.liga.console_parcels.dto.TruckPackageAlgorithm;
import ru.liga.console_parcels.dto.UnpackedTruckDto;
import ru.liga.console_parcels.entity.Truck;
import ru.liga.console_parcels.formatter.ResultFormatter;
import ru.liga.console_parcels.service.FileDownloadService;
import ru.liga.console_parcels.service.PackagingManager;
import ru.liga.console_parcels.service.TruckParcelsUnpackingService;

import java.nio.file.Path;
import java.util.List;

/**
 * REST-контроллер для управления грузами, предоставляет API для упаковки и распаковки грузовиков.
 */
@RestController
@Log4j2
@RequestMapping("/trucks")
@RequiredArgsConstructor
public class CargoManagementRestController {
    private final PackagingManager packagingManager;
    private final TruckParcelsUnpackingService truckParcelsUnpackingService;
    private final FileDownloadService fileDownloadService;
    private final ResultFormatter resultFormatter;

    /**
     * Обрабатывает запрос на упаковку без использования файла.
     *
     * @param packRequest запрос на упаковку, содержащий информацию о грузовиках и посылках
     * @return строковое представление результатов упаковки и статус 200 (OK)
     */
    @PostMapping("/pack")
    public ResponseEntity<String> packWithoutFile(@RequestBody @Valid PackRequestDto packRequest) {
        log.info("Пользователь выбрал упаковку без файла.");

        List<Truck> packedTrucks = packagingManager.pack(packRequest);

        String response = resultFormatter.convertPackagingResultsToString(packedTrucks);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Обрабатывает запрос на упаковку с использованием файла.
     *
     * @param trucks строковое представление грузовиков
     * @param file файл в формате .txt с данными для упаковки
     * @param packageAlgorithm выбор алгоритма упаковки
     * @param filePathToWrite путь к файлу в формате .json для записи результатов упаковки
     * @return строковое представление результатов упаковки и статус 200 (OK)
     */
    @PostMapping("/pack/file")
    public ResponseEntity<String> packWithFile(@RequestParam("trucks") String trucks,
                                               @RequestParam("file") MultipartFile file,
                                               @RequestParam("packageAlgorithm") TruckPackageAlgorithm packageAlgorithm,
                                               @RequestParam("filePathToWrite") String filePathToWrite) {
        log.info("Пользователь выбрал упаковку с файлом.");

        Path path = fileDownloadService.download(file);

        String fullPath = path.toAbsolutePath().toString();

        PackRequestDto packRequest = new PackRequestDto(trucks, fullPath, packageAlgorithm, filePathToWrite);

        List<Truck> packedTrucks = packagingManager.pack(packRequest);

        String response = resultFormatter.convertPackagingResultsToString(packedTrucks);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Обрабатывает запрос на распаковку.
     *
     * @param file файл в формате .json с данными для распаковки
     * @return строковое представление результатов распаковки и статус 200 (OK)
     */
    @PostMapping("/unpack")
    public ResponseEntity<String> unpack(@RequestParam("trucks") MultipartFile file) {
        log.info("Пользователь выбрал распаковку.");

        Path path = fileDownloadService.download(file);

        String fullPath = path.toAbsolutePath().toString();

        List<UnpackedTruckDto> packedTruck = truckParcelsUnpackingService.unpack(fullPath);

        String response = resultFormatter.convertUnpackingResultsToString(packedTruck);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
