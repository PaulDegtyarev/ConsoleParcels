package ConsoleParcelsApp.service;

import ConsoleParcelsApp.dto.UnPackedTruckDto;

import java.io.IOException;
import java.util.List;

public interface UnPackagingService {


    List<UnPackedTruckDto> unpackTruck(String filePath) throws IOException;
}
