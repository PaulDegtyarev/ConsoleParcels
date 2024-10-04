package ru.liga.consoleParcels.service;

import java.util.Map;

public interface ParcelQuantityRecordingService {
    void writeParcelCountToJsonFile(Map<String, Integer> parcelCountByShape);
}
