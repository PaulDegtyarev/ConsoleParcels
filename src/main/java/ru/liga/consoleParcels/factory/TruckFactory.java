package ru.liga.consoleParcels.factory;

import ru.liga.consoleParcels.model.Truck;

import java.util.List;

/**
 * Интерфейс фабрики грузовиков.
 *
 * <p>
 * Интерфейс определяет метод для создания списка грузовиков на основе строки с размерами.
 * </p>
 */
public interface TruckFactory {
    /**
     * Метод для создания списка грузовиков на основе строки с размерами.
     *
     * @param truckSizes Строка с размерами грузовиков в формате "5x5, 8X5,, ...".
     * @return Список объектов {@link Truck}, созданных на основе строки размеров.
     */
    List<Truck> createTrucks(String truckSizes);
}
