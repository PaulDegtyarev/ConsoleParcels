package ConsoleParcelsApp.service;

import ConsoleParcelsApp.dto.UnPackedTruckDto;
import ConsoleParcelsApp.model.Truck;

import java.util.List;

public interface PrintResultService {
    void printPackagingResults(List<Truck> trucks);

    void printUnPackagingResults(List<UnPackedTruckDto> unPackedTrucks);
}
