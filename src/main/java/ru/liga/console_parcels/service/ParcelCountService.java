package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.TruckParcelCountDto;
import ru.liga.console_parcels.entity.Truck;

import java.util.List;

/**
 * Интерфейс для подсчета количества посылок в грузовиках.
 */
public interface ParcelCountService {

    /**
     * Подсчитывает количество посылок в каждом грузовике.
     *
     * @param trucks Список грузовиков.
     * @return Список DTO, содержащих информацию о количестве посылок в каждом грузовике.
     */
    List<TruckParcelCountDto> count(List<Truck> trucks);
}
