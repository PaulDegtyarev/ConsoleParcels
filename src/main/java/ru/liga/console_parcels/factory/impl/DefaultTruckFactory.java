package ru.liga.console_parcels.factory.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.liga.console_parcels.factory.TruckFactory;
import ru.liga.console_parcels.model.Truck;

import java.util.ArrayList;
import java.util.List;

/**
 * Фабрика грузовиков по умолчанию.
 *
 * <p>
 * Этот класс реализует интерфейс {@link TruckFactory} и предоставляет метод для создания списка грузовиков на основе строки с размерами.
 * Класс использует {@link Log4j2} для логирования процесса создания грузовиков.
 * </p>
 */
@Log4j2
@Component
public class DefaultTruckFactory implements TruckFactory {
    /**
     * Метод для создания списка грузовиков на основе строки с размерами.
     *
     * @param truckSizes Строка с размерами грузовиков в формате "5x5, 8X5, ...".
     * @return Список объектов {@link Truck}, созданных на основе строки размеров.
     */
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
            log.debug("Создан грузовик {}x{}", width, height);
        }

        return trucks;
    }
}
