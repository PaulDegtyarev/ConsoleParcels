package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.ParcelForPackaging;
import ru.liga.consoleParcels.mapper.ParcelMapper;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

/**
 * Сервис для упаковки посылок в грузовики.
 * <p>
 * Этот интерфейс определяет метод для упаковки списка
 * посылок в заданное количество грузовиков.
 */
public interface PackagingService {

    List<Truck> packPackages(List<ParcelForPackaging> parcels, String trucks);
}
