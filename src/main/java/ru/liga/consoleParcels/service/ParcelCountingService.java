package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.ParcelForPackagingDto;

import java.util.List;
import java.util.Map;

public interface ParcelCountingService {
    Map<String, Integer> countParcelByShape(List<ParcelForPackagingDto> parcels);
}
