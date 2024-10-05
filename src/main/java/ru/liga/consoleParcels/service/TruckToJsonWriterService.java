package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.model.Truck;

import java.util.List;

public interface TruckToJsonWriterService {
    void writeTruckToJson(List<Truck> trucks, String filePath);
}
