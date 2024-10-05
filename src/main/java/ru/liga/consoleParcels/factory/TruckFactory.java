package ru.liga.consoleParcels.factory;

import ru.liga.consoleParcels.model.Truck;

import java.util.List;

public interface TruckFactory {

    List<Truck> createTrucks(String truckSizes);
}
