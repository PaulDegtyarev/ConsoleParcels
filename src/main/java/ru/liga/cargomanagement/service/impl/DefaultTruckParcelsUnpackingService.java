package ru.liga.cargomanagement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.cargomanagement.dto.UnpackedTruckDto;
import ru.liga.cargomanagement.service.TruckParcelsUnpackingService;
import ru.liga.cargomanagement.service.FileUnpackingService;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class DefaultTruckParcelsUnpackingService implements TruckParcelsUnpackingService {
    private final FileUnpackingService fileUnpackagingService;

    @Override
    public List<UnpackedTruckDto> unpack(String truckFilePath) {
        log.info("Начало процесса распаковки");

        List<UnpackedTruckDto> unpackedTrucks = fileUnpackagingService.unpackTruck(truckFilePath);
        log.info("Распаковка завершена. Распаковано {} машин", unpackedTrucks.size());
        return unpackedTrucks;
    }
}
