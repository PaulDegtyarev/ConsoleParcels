package ru.liga.cargomanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.cargomanagement.dto.UnpackedTruckDto;
import ru.liga.cargomanagement.formatter.ResultFormatter;
import ru.liga.cargomanagement.service.DocumentHandler;
import ru.liga.cargomanagement.service.TruckParcelsUnpackingService;

import java.io.File;
import java.util.List;

@Service("jsonDocumentHandler")
@RequiredArgsConstructor
public class JsonDocumentHandler implements DocumentHandler {
    private final ResultFormatter resultFormatter;
    private final TruckParcelsUnpackingService truckParcelsUnpackingService;

    @Override
    public String handle(File file, Message message) {
        List<UnpackedTruckDto> unpackedTruck = truckParcelsUnpackingService.unpack(file.getAbsolutePath());

        return resultFormatter.convertUnpackingResultsToString(unpackedTruck);
    }
}
