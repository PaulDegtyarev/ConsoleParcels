package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.TruckParcelCountDto;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

/**
 * Интерфейс для подсчета количества посылок в грузовиках.
 */
public interface ParcelCountingService {

    /**
     * Подсчитывает количество посылок в каждом грузовике.
     *
     * @param trucks Список грузовиков.
     * @return Список DTO, содержащих информацию о количестве посылок в каждом грузовике.
     */
    List<TruckParcelCountDto> countParcelsInTrucks(List<Truck> trucks);
}
