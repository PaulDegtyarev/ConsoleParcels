package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

public interface PackagingService {
    List<Truck> packPackages(List<ParcelForPackagingDto> parcels, String trucks);
}
