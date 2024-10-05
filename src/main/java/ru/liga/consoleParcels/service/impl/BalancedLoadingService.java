package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.dto.TruckParcelCountDto;
import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.model.Point;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.model.TruckPlacement;
import ru.liga.consoleParcels.service.PackagingService;
import ru.liga.consoleParcels.service.ParcelCountingService;
import ru.liga.consoleParcels.service.ParcelQuantityRecordingService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для упаковки посылок со сбалансированной загрузкой грузовиков.
 */
@Log4j2
@Service
public class BalancedLoadingService implements PackagingService {
    private TruckFactory truckFactory;
    private ParcelCountingService parcelCountingService;
    private ParcelQuantityRecordingService parcelQuantityRecordingService;

    /**
     * Конструктор сервиса с зависимостями.
     *
     * @param truckFactory                   Фабрика грузовиков.
     * @param parcelCountingService          Сервис подсчета посылок.
     * @param parcelQuantityRecordingService Сервис записи количества посылок.
     */
    @Autowired
    public BalancedLoadingService(TruckFactory truckFactory, ParcelCountingService parcelCountingService, ParcelQuantityRecordingService parcelQuantityRecordingService) {
        this.truckFactory = truckFactory;
        this.parcelCountingService = parcelCountingService;
        this.parcelQuantityRecordingService = parcelQuantityRecordingService;
        log.info("BalancedLoadingService инициализирован");
    }

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