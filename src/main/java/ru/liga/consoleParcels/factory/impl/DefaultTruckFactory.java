package ru.liga.consoleParcels.factory.impl;

import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.model.Truck;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация фабрики для создания грузовиков.
 * <p>
 * Эта фабрика создает список грузовиков заданной длины,
 * инициализируя каждый грузовик как пустой объект {@link Truck}.
 *
 * @see TruckFactory
 */
@Log4j2
public class DefaultTruckFactory implements TruckFactory {

    @Override
    public List<Truck> createTrucks(String truckSizes) {
        List<Truck> trucks = new ArrayList<>();

        String[] sizePairs = truckSizes.split(", ");
        for (String sizePair : sizePairs) {
            String[] heightAndWidth = sizePair.split("x");
            int height = Integer.parseInt(heightAndWidth[0]);
            int width = Integer.parseInt(heightAndWidth[1]);

            Truck truck = new Truck(width, height);
            log.debug("Создан грузовик размерами {} x {}", height, width);
            trucks.add(truck);
            log.debug("Создан грузовик {}x{}", width, height);
        }

        return trucks;
    }
}
