package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.UnpackedTruckDto;

import java.util.List;

/**
 * Интерфейс для управления распаковкой грузовиков.
 */
public interface TruckParcelsUnpackingService {

    List<UnpackedTruckDto> unpack(String truckFilePath);
}
