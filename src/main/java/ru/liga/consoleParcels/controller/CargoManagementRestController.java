package ru.liga.consoleParcels.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.consoleParcels.dto.PackRequestDto;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;
import ru.liga.consoleParcels.service.FileDownloadService;
import ru.liga.consoleParcels.service.PackagingManager;
import ru.liga.consoleParcels.service.UnPackagingManager;

import java.nio.file.Path;

@RestController
@Log4j2
@RequestMapping("/trucks")
@RequiredArgsConstructor
public class CargoManagementRestController {
    private final PackagingManager packagingManager;
    private final UnPackagingManager unPackagingManager;
    private FileDownloadService fileDownloadService;

    @PostMapping("/pack")
    public ResponseEntity<String> packWithoutFile(@RequestBody @Valid PackRequestDto packRequest) {
        return new ResponseEntity<>(packagingManager.packParcels(packRequest), HttpStatus.OK);
    }

    @PostMapping("/pack/file")
    public ResponseEntity<String> packWithFile(@RequestParam("trucks") String trucks,
                                               @RequestParam("file") MultipartFile file,
                                               @RequestParam("algorithmChoice") UserAlgorithmChoice algorithmChoice,
                                               @RequestParam("filePathToWrite") String filePathToWrite) {
        Path path = fileDownloadService.download(file);

        String fullPath = path.toAbsolutePath().toString();

        PackRequestDto packRequest = new PackRequestDto(trucks, fullPath, algorithmChoice, filePathToWrite);

        String packedTruck = packagingManager.packParcels(packRequest);

        return new ResponseEntity<>(packedTruck, HttpStatus.OK);
    }

    @PostMapping("/unpack")
    public ResponseEntity<String> unpack(@RequestParam("trucks") MultipartFile file) {
        Path path = fileDownloadService.download(file);

        String fullPath = path.toAbsolutePath().toString();

        String packedTruck = unPackagingManager.unpackParcels(fullPath);
        
        return new ResponseEntity<>(packedTruck, HttpStatus.OK);

    }
}
