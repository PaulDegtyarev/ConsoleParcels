package ru.liga.console_parcels.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.ParcelForPackagingDto;
import ru.liga.console_parcels.dto.TruckParcelCountDto;
import ru.liga.console_parcels.exception.PackingException;
import ru.liga.console_parcels.factory.TruckFactory;
import ru.liga.console_parcels.model.Point;
import ru.liga.console_parcels.model.Truck;
import ru.liga.console_parcels.model.TruckPlacement;
import ru.liga.console_parcels.service.TruckPackageService;
import ru.liga.console_parcels.service.ParcelCountingService;
import ru.liga.console_parcels.service.ParcelQuantityRecordingService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для упаковки посылок со сбалансированной загрузкой грузовиков.
 */
@Log4j2
@Service
@Qualifier("EVEN_LOADING")
@RequiredArgsConstructor
public class BalancedLoadingService implements TruckPackageService {
    private final TruckFactory truckFactory;
    private final ParcelCountingService parcelCountingService;
    private final ParcelQuantityRecordingService parcelQuantityRecordingService;

    /**
     * Упаковывает посылки в грузовики.
     *
     * @param parcels    Список посылок для упаковки.
     * @param trucksSize Размер грузовиков.
     * @return Список грузовиков с упакованными посылками.
     */
    @Override
    public List<Truck> packPackages(List<ParcelForPackagingDto> parcels, String trucksSize) {
        log.info("Начало упаковки посылок. Количество посылок: {}, размер грузовиков: {}", parcels.size(), trucksSize);

        parcels.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));
        log.debug("Посылки отсортированы по площади в порядке убывания");

        log.trace("Отсортированные посылки: {}", parcels);

        List<Truck> trucks = loadTrucks(parcels, trucksSize);
        log.info("Загрузка грузовиков завершена. Количество использованных грузовиков: {}", trucks.size());

        List<TruckParcelCountDto> truckParcelCounts = parcelCountingService.countParcelsInTrucks(trucks);
        log.debug("Подсчет посылок в грузовиках завершен");

        parcelQuantityRecordingService.writeParcelCountToJsonFile(truckParcelCounts);
        log.info("Запись количества посылок в JSON-файл завершена");

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private List<Truck> loadTrucks(List<ParcelForPackagingDto> parcels, String trucksSize) {
        List<Truck> trucks = truckFactory.createTrucks(trucksSize);
        log.debug("Создано {} грузовиков", trucks.size());

        for (int i = 0; i < parcels.size(); i++) {
            ParcelForPackagingDto parcel = parcels.get(i);
            log.debug("Обработка посылки {}/{}: {}", i + 1, parcels.size(), parcel);

            Optional<TruckPlacement> bestPlacement = trucks.stream()
                    .map(truck -> {
                        Optional<Point> position = truck.findPosition(parcel);
                        return position.map(point -> new TruckPlacement(truck, point));
                    })
                    .flatMap(Optional::stream)
                    .min(Comparator.comparingInt(tp -> tp.getTruck().getUsedSpace()));

            if (bestPlacement.isPresent()) {
                TruckPlacement truckPlacement = bestPlacement.get();
                Truck truck = truckPlacement.getTruck();
                Point position = truckPlacement.getPosition();
                truck.place(parcel, position.getX(), position.getY());
                log.info("Посылка размещена в грузовике {} на позиции ({}, {})",
                        trucks.indexOf(truck) + 1, position.getX(), position.getY());
            } else {
                log.error("Не удалось разместить посылку: {}", parcel);
                throw new PackingException("Не удалось разместить посылку: " + parcel);
            }
        }

        log.debug("Загрузка грузовиков завершена. Состояние грузовиков: {}",
                trucks.stream().map(t -> "Грузовик " + (trucks.indexOf(t) + 1) + ": " + t.getUsedSpace() + " занято")
                        .collect(Collectors.joining(", ")));

        return trucks;
    }
}