package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.dto.TruckParcelCountDto;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;
import java.util.Map;

public interface ParcelCountingService {

    List<TruckParcelCountDto> countParcelsInTrucks(List<Truck> trucks);
}
