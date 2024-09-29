package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.mapper.ParcelMapper;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

/**
 * Сервис для упаковки посылок в грузовики.
 * <p>
 * Этот интерфейс определяет метод для упаковки списка
 * посылок в заданное количество грузовиков.
 */
public interface PackagingService {
    List<Truck> packPackages(List<ParcelMapper> parcels, int numberOfCars);

    /**
     * Упаковывает список посылок в заданное количество грузовиков.
     *
     * @param parcels      Список посылок, которые нужно упаковать.
     * @param numberOfCars Количество грузовиков, в которые нужно
     *                     упаковать посылки.
     * @return Список грузовиков с упакованными посылками.
     */

}
