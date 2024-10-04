package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.consoleParcels.dto.ParcelCountDto;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * Реализация сервиса упаковки с равномерной загрузкой.
 * <p>
 * Этот сервис сортирует посылки по площади в порядке убывания
 * и затем распределяет их по грузовикам, выбирая для каждой
 * посылки грузовик с наименьшим заполненным пространством.
 *
 * @see PackagingService
 */
@Log4j2
public class BalancedLoadingService implements PackagingService {
    private TruckFactory truckFactory;
    private ParcelCountingService parcelCountingService;
    private ParcelQuantityRecordingService parcelQuantityRecordingService;

    /**
     * Конструктор сервиса упаковки с равномерной загрузкой.
     *
     * @param truckFactory Фабрика для создания грузовиков.
     */
    @Autowired
    public BalancedLoadingService(TruckFactory truckFactory, ParcelCountingService parcelCountingService, ParcelQuantityRecordingService parcelQuantityRecordingService) {
        this.truckFactory = truckFactory;
        this.parcelCountingService = parcelCountingService;
        this.parcelQuantityRecordingService = parcelQuantityRecordingService;
    }


    @Override
    public List<Truck> packPackages(List<ParcelForPackagingDto> parcels, String trucksSize) {
        parcels.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));
        log.trace("Посылки отсортированы по площади в порядке убывания");

        List<Truck> trucks = loadTrucks(parcels, trucksSize);

        List<TruckParcelCountDto> truckParcelCounts = countParcelsInTrucks(trucks);

        parcelQuantityRecordingService.writeParcelCountToJsonFile(truckParcelCounts);

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private List<TruckParcelCountDto> countParcelsInTrucks(List<Truck> trucks) {
        List<TruckParcelCountDto> truckParcelCounts = new ArrayList<>();

        for (int i = 0; i < trucks.size(); i++) {
            Truck truck = trucks.get(i);
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
        }

        return truckParcelCounts;
    }

    private List<Truck> loadTrucks(List<ParcelForPackagingDto> parcels, String trucksSize) {
        List<Truck> trucks = truckFactory.createTrucks(trucksSize);

        parcels.forEach(parcel -> {
            Optional<TruckPlacement> bestPlacement = trucks.stream()
                    .map(truck -> {
                        Optional<Point> position = truck.findPosition(parcel);
                        return position.map(point -> new TruckPlacement(truck, point));
                    })
                    .flatMap(Optional::stream)
                    .min(Comparator.comparingInt(tp -> tp.getTruck().getUsedSpace()));

            bestPlacement.ifPresentOrElse(truckPlacement -> {
                Truck truck = truckPlacement.getTruck();
                Point position = truckPlacement.getPosition();
                truck.place(parcel, position.getX(), position.getY());
                log.info("Посылка размещена в грузовике на позиции ({}, {})",
                        position.getX(), position.getY());
            }, () -> {
                throw new PackingException("Не удалось разместить посылку: " + parcel);
            });
        });

        return trucks;
    }
}