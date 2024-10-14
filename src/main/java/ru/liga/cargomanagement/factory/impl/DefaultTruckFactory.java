package ru.liga.cargomanagement.factory.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.liga.cargomanagement.entity.Truck;
import ru.liga.cargomanagement.factory.TruckFactory;

import java.util.ArrayList;
import java.util.List;


@Log4j2
@Component
public class DefaultTruckFactory implements TruckFactory {
    @Override
    public List<Truck> createTrucks(String truckSizes) {
        int indexOfHeight = 0;
        int indexOfWidth = 1;
        List<Truck> trucks = new ArrayList<>();

        String[] sizePairs = truckSizes.split(", ");
        for (String sizePair : sizePairs) {
            String[] heightAndWidth = sizePair.split("x");
            int height = Integer.parseInt(heightAndWidth[indexOfHeight]);
            int width = Integer.parseInt(heightAndWidth[indexOfWidth]);

            Truck truck = new Truck(width, height);
            log.debug("Создан грузовик размерами {} x {}", height, width);
            trucks.add(truck);
        }

        return trucks;
    }
}
