package ConsoleParcelsApp.service;

import ConsoleParcelsApp.model.Truck;

import java.util.List;

public interface TruckToJsonWriterService {
    void writeTruckToJson(List<Truck> trucks);
}
