package ru.liga.consoleParcels.factory.impl;

import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.model.Truck;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class TruckFactoryImpl implements TruckFactory {
    @Override
    public List<Truck> createTrucks(int numberOfCars) {
        List<Truck> trucks = new ArrayList<>(numberOfCars);

        for (int i = 0; i < numberOfCars; i++) {
            Truck truck = new Truck();
            trucks.add(truck);
            log.debug("Создан грузовик №{}", i + 1);
        }

        return trucks;
    }
}
