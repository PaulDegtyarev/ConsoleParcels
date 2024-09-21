package ru.liga.parcels.service;

import ru.liga.parcels.model.Parcel;
import ru.liga.parcels.model.Truck;

import java.util.List;

public interface PackagingService {

    List<Truck> packPackages(List<Parcel> parcels, int numberOfCars);
}
