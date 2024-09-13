package ConsoleParcelsApp.service;

import ConsoleParcelsApp.model.Truck;

import java.util.List;

public interface PackagingService {

    List<Truck> packPackages(String filePath);
}
