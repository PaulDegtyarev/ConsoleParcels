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
    /**
     * Создает список грузовиков заданной длины.
     *
     * @param numberOfCars Количество грузовиков, которые нужно создать.
     * @return Список созданных грузовиков.
     */
    List<Truck> createTrucks(int numberOfCars);
}
