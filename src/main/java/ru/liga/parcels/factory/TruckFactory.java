package ru.liga.parcels.factory;

import ru.liga.parcels.model.Truck;

import java.util.List;

public interface TruckFactory {
    List<Truck> createTrucks(int numberOfCars);
}
