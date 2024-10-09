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
import ru.liga.console_parcels.formatter.ResultFormatter;
import ru.liga.console_parcels.service.FileDownloadService;
import ru.liga.console_parcels.service.PackagingManager;
import ru.liga.console_parcels.service.TruckParcelsUnpackingService;

import java.nio.file.Path;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/trucks")
@RequiredArgsConstructor
public class CargoManagementRestController {
    private final PackagingManager packagingManager;
    private final TruckParcelsUnpackingService truckParcelsUnpackingService;
    private final FileDownloadService fileDownloadService;
    private final ResultFormatter resultFormatter;

    @PostMapping("/pack")
    public ResponseEntity<String> packWithoutFile(@RequestBody @Valid PackRequestDto packRequest) {
        log.info("Пользователь выбрал упаковку без файла.");
        return new ResponseEntity<>(packagingManager.packParcels(packRequest), HttpStatus.OK);
    }

    @PostMapping("/pack/file")
    public ResponseEntity<String> packWithFile(@RequestParam("trucks") String trucks,
                                               @RequestParam("file") MultipartFile file,
                                               @RequestParam("algorithmChoice") TruckPackageAlgorithm algorithmChoice,
                                               @RequestParam("filePathToWrite") String filePathToWrite) {
        log.info("Пользователь выбрал упаковку с файлом.");

        Path path = fileDownloadService.download(file);

        String fullPath = path.toAbsolutePath().toString();

        PackRequestDto packRequest = new PackRequestDto(trucks, fullPath, algorithmChoice, filePathToWrite);

        String packedTruck = packagingManager.packParcels(packRequest);

        return new ResponseEntity<>(packedTruck, HttpStatus.OK);
    }

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
