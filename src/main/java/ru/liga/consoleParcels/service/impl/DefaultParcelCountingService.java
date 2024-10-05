package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelCountDto;
import ru.liga.consoleParcels.dto.TruckParcelCountDto;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.ParcelCountingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DefaultParcelCountingService implements ParcelCountingService {
    @Override
    public List<TruckParcelCountDto> countParcelsInTrucks(List<Truck> trucks) {
        log.info("Начало подсчета количества посылок в грузовиках. Количество грузовиков: {}", trucks.size());

        List<TruckParcelCountDto> truckParcelCounts = new ArrayList<>();

        for (int i = 0; i < trucks.size(); i++) {
            Truck truck = trucks.get(i);
            log.debug("Обработка грузовика №{}...", i + 1);

            Map<String, Integer> parcelCountByShape = new HashMap<>();
            for (char[] row : truck.getSpace()) {
                for (char cell : row) {
                    if (cell != ' ') {
                        String shape = String.valueOf(cell);
                        parcelCountByShape.put(shape, parcelCountByShape.getOrDefault(shape, 0) + 1);
                    }
                }
            }

            List<ParcelCountDto> parcelCounts = parcelCountByShape.entrySet().stream()
                    .map(entry -> new ParcelCountDto(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

            truckParcelCounts.add(new TruckParcelCountDto(i + 1, parcelCounts));
            log.debug("Подсчет посылок в грузовике №{} завершен. Найдено {} типов посылок.", i + 1, parcelCounts.size());
        }

        log.info("Подсчет количества посылок во всех грузовиках завершен. Найдено {} записей.", truckParcelCounts.size());
        return truckParcelCounts;
    }
}
