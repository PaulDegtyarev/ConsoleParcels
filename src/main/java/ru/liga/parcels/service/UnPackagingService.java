package ru.liga.parcels.service;

import ru.liga.parcels.dto.UnPackedTruckDto;

import java.util.List;

public interface UnPackagingService {
    List<UnPackedTruckDto> unpackTruck(String filePath);
}
