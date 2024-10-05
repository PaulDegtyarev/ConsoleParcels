package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.TruckParcelCountDto;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

public interface ParcelCountingService {

    List<TruckParcelCountDto> countParcelsInTrucks(List<Truck> trucks);
}
