package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.TruckParcelCountDto;

import java.util.List;
import java.util.Map;

public interface ParcelQuantityRecordingService {

    void writeParcelCountToJsonFile(List<TruckParcelCountDto> truckParcelCounts);
}
