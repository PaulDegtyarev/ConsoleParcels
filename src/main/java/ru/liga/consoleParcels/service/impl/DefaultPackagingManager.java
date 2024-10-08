package ru.liga.consoleParcels.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.PackRequestDto;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.exception.ParcelNotFoundException;
import ru.liga.consoleParcels.formatter.PrintResultFormatter;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.repository.ParcelRepository;
import ru.liga.consoleParcels.service.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация менеджера упаковки посылок.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class DefaultPackagingManager implements PackagingManager {
    private final PackagingSelectionService packagingSelectionService;
    private final TruckToJsonWriterService truckToJsonWriterService;
    private final PrintResultFormatter printResultFormatter;
    private final PackageReader packageReader;
    private final ParcelRepository parcelRepository;

    /**
     * Упаковывает посылки согласно запросу.
     *
     * @param packRequestDto Запрос на упаковку.
     * @return Строка с результатами упаковки.
     */
    @Override
    public String packParcels(PackRequestDto packRequestDto) {
        log.info("Начало процесса упаковки");

        PackagingService packagingService = packagingSelectionService.selectPackagingService(packRequestDto.getAlgorithmChoice());
        log.debug("Выбран сервис для упаковки: {}", packagingService.getClass().getSimpleName());

        List<ParcelForPackagingDto> parcelsForPackaging;

        try {
            parcelsForPackaging = createParcelsForPackagingFromNames(packRequestDto);

            log.info("Получено {} посылок из репозитория", parcelsForPackaging.size());
        } catch (ParcelNotFoundException parcelNotFoundException) {
            parcelsForPackaging = packageReader.readPackages(packRequestDto.getInputData());
            log.debug("Прочитано {} посылок из файла {}", parcelsForPackaging.size(), packRequestDto.getInputData());
        }

        List<Truck> trucks;

        log.info("Начинается упаковка {} машин из файла {}", packRequestDto.getTrucks(), packRequestDto.getInputData());
        trucks = packagingService.packPackages(parcelsForPackaging, packRequestDto.getTrucks());
        log.info("Упаковка завершена. Упаковано {} грузовиков", trucks.size());

        truckToJsonWriterService.writeTruckToJson(trucks, packRequestDto.getFilePathToWrite());
        log.info("Запись результатов упаковки в JSON завершена");

        log.info("Начало печати результатов упаковки для {} грузовиков", trucks.size());
        return printResultFormatter.transferPackagingResultsToString(trucks);
    }

    private List<ParcelForPackagingDto> createParcelsForPackagingFromNames(PackRequestDto packRequestDto) {
        int firstRowIndex = 0;

        return Arrays.stream(packRequestDto.getInputData().split(","))
                .map(parcelName -> {
                    Parcel parcel = parcelRepository.findParcelByName(parcelName.trim().toLowerCase())
                            .orElseThrow(() -> new ParcelNotFoundException("Посылка с именем " + parcelName + " не найдена."));
                    return new ParcelForPackagingDto(
                            parcel.getShape().length,
                            parcel.getShape()[firstRowIndex].length,
                            parcel.getShape()
                    );
                })
                .toList();
    }
}
