package ru.liga.console_parcels.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.PackRequestDto;
import ru.liga.console_parcels.dto.ParcelForPackagingDto;
import ru.liga.console_parcels.entity.Parcel;
import ru.liga.console_parcels.entity.Truck;
import ru.liga.console_parcels.exception.ParcelNotFoundException;
import ru.liga.console_parcels.repository.ParcelRepository;
import ru.liga.console_parcels.service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class DefaultPackagingManager implements PackagingManager {
    private final PackagingSelectionService packagingSelectionService;
    private final FileWriterService fileWriterService;
    private final PackageReader packageReader;
    private final ParcelRepository parcelRepository;

    @Override
    public List<Truck> pack(PackRequestDto packRequestDto) {
        TruckPackageService truckPackageService = packagingSelectionService.selectPackagingService(packRequestDto.getPackageAlgorithm());
        log.debug("Выбран сервис для упаковки: {}", truckPackageService.getClass().getSimpleName());

        List<ParcelForPackagingDto> parcelsForPackaging;
        try {
            parcelsForPackaging = convertParcelRequestToParcels(packRequestDto);
            log.info("Получено {} посылок из репозитория", parcelsForPackaging.size());
        } catch (ParcelNotFoundException parcelNotFoundException) {
            parcelsForPackaging = packageReader.read(packRequestDto.getInputData());
            log.info("Прочитано {} посылок из файла {}", parcelsForPackaging.size(), packRequestDto.getInputData());
        }

        List<Truck> trucks = truckPackageService.packPackages(parcelsForPackaging, packRequestDto.getTrucks());

        fileWriterService.write(trucks, packRequestDto.getFilePathToWrite());

        return trucks;
    }

    private List<ParcelForPackagingDto> convertParcelRequestToParcels(PackRequestDto packRequestDto) {
        int indexOfWidth = 0;
        return new ArrayList<>(Arrays.stream(packRequestDto.getInputData()
                        .split(","))
                .toList()
                .stream()
                .map(String::trim)
                .map(parcelName -> {
                    Parcel parcel = parcelRepository.findParcelByName(parcelName.trim().toLowerCase())
                            .orElseThrow(() -> new ParcelNotFoundException("Посылка с именем " + parcelName + " не найдена."));
                    return new ParcelForPackagingDto(
                            parcel.convertStringToCharArray(parcel.getShape()).length,
                            parcel.convertStringToCharArray(parcel.getShape())[indexOfWidth].length,
                            parcel.convertStringToCharArray(parcel.getShape())
                    );
                })
                .toList());
    }
}
