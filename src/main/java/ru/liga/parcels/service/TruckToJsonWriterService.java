package ru.liga.parcels.service;

import ru.liga.parcels.model.Truck;

import java.util.List;

public interface TruckToJsonWriterService {
    void writeTruckToJson(List<Truck> trucks, String filePath);
}
