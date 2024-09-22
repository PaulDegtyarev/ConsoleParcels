package ru.liga.parcels.service;

import ru.liga.parcels.dto.UnPackedTruckDto;
import ru.liga.parcels.model.Truck;

import java.util.List;
import java.util.Optional;

public interface PrintResultService {

    void printPackagingResults(Optional<List<Truck>> trucks);


    void printUnPackagingResults(Optional<List<UnPackedTruckDto>> unPackedTrucks);
}
