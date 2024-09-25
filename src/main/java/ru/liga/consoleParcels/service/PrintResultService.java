package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;
import java.util.Optional;

public interface PrintResultService {

    void printPackagingResults(Optional<List<Truck>> trucks);


    void printUnPackagingResults(Optional<List<UnPackedTruckDto>> unPackedTrucks);
}
