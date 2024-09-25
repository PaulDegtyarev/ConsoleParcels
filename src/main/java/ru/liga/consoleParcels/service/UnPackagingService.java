package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.UnPackedTruckDto;

import java.util.List;

public interface UnPackagingService {
    List<UnPackedTruckDto> unpackTruck(String filePath);
}
