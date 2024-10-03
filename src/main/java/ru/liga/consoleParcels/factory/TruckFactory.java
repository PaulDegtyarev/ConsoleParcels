package ru.liga.consoleParcels.factory;

import ru.liga.consoleParcels.model.Truck;

import java.util.List;

/**
 * Фабрика для создания грузовиков.
 * <p>
 * Этот интерфейс определяет метод для создания списка грузовиков
 * заданной длины.
 */
public interface TruckFactory {

    List<Truck> createTrucks(String truckSizes);
}
