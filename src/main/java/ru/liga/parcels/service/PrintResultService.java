package ru.liga.parcels.service;

import ru.liga.parcels.dto.UnPackedTruckDto;
import ru.liga.parcels.model.Truck;

import java.util.List;

public interface PrintResultService {
    void printPackagingResults(List<Truck> trucks);

    void printUnPackagingResults(List<UnPackedTruckDto> unPackedTrucks);
}
