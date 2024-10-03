package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.PackRequest;
import ru.liga.consoleParcels.dto.ParcelForPackaging;
import ru.liga.consoleParcels.exception.ParcelNotFoundException;
import ru.liga.consoleParcels.formatter.PrintResultFormatter;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.repository.ParcelRepository;
import ru.liga.consoleParcels.service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DefaultPackagingManager implements PackagingManager {
    private PackagingSelectionService packagingSelectionService;
    private TruckToJsonWriterService truckToJsonWriterService;
    private PrintResultFormatter printResultFormatter;
    private PackageReader packageReader;
    private ParcelRepository parcelRepository;

    @Autowired
    public DefaultPackagingManager(PackagingSelectionService packagingSelectionService, TruckToJsonWriterService truckToJsonWriterService, PrintResultFormatter printResultFormatter, PackageReader packageReader, ParcelRepository parcelRepository) {
        this.packagingSelectionService = packagingSelectionService;
        this.truckToJsonWriterService = truckToJsonWriterService;
        this.printResultFormatter = printResultFormatter;
        this.packageReader = packageReader;
        this.parcelRepository = parcelRepository;
    }

    @Override
    public String packParcels(PackRequest packRequest) {
        log.info("Начало процесса упаковки");

        PackagingService packagingService = packagingSelectionService.selectPackagingService(packRequest.getAlgorithmChoice());
        log.debug("Выбран сервис для упаковки: {}", packagingService);

        List<ParcelForPackaging> parcelsForPackaging;
        try {
            String[] inputDataElements = packRequest.getInputData().split(",");
            List<String> parcelNames = Arrays.stream(inputDataElements)
                    .map(String::trim)
                    .toList();

            parcelsForPackaging = parcelNames.stream()
                    .map(parcelName -> {
                        Parcel parcel = parcelRepository.findParcelByName(parcelName.trim().toLowerCase())
                                .orElseThrow(() -> new ParcelNotFoundException("Посылка с именем " + parcelName + " не найдена."));
                        return new ParcelForPackaging(
                                parcel.getShape().length,
                                parcel.getShape()[0].length,
                                parcel.getShape()
                        );
                    })
                    .collect(Collectors.toList());

        } catch (ParcelNotFoundException parcelNotFoundException) {
            parcelsForPackaging = packageReader.readPackages(packRequest.getInputData());
            log.debug("Прочитано {} посылок из файла {}", parcelsForPackaging.size(), packRequest.getInputData());
        }

        List<Truck> trucks;

        log.info("Начинается упаковка {} машин из файла {}", packRequest.getTrucks(), packRequest.getInputData());
        trucks = packagingService.packPackages(parcelsForPackaging, packRequest.getTrucks());
        log.info("Упаковка завершена. Упаковано {} грузовиков", trucks.size());

        truckToJsonWriterService.writeTruckToJson(trucks, packRequest.getFilePathToWrite());
        log.info("Запись результатов упаковки в JSON завершена");

        log.info("Начало печати результатов упаковки для {} грузовиков", trucks.size());

        return printResultFormatter.transferPackagingResultsToConsole(trucks).toString();
    }
}
