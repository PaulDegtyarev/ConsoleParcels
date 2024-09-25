package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

public interface PackagingService {

    List<Truck> packPackages(List<Parcel> parcels, int numberOfCars);
}
