package ConsoleParcelsApp.factory;

import ConsoleParcelsApp.model.Truck;

import java.util.List;

public interface TruckFactory {
    List<Truck> createTrucks(int numberOfCars);
}
