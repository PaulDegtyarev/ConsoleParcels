package ConsoleParcelsApp.service;

import ConsoleParcelsApp.dto.UnPackedTruckDto;

import java.util.List;

public interface UnPackagingService {
    List<UnPackedTruckDto> unpackTruck(String filePath);
}
