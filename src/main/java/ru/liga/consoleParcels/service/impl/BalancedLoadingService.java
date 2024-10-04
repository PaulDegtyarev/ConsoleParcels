package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.model.Point;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.model.TruckPlacement;
import ru.liga.consoleParcels.service.PackagingService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    /**
     * Конструктор сервиса упаковки с равномерной загрузкой.
     *
     * @param truckFactory Фабрика для создания грузовиков.
     */
    public BalancedLoadingService(TruckFactory truckFactory) {
        this.truckFactory = truckFactory;
    }


    @Override
    public List<Truck> packPackages(List<ParcelForPackagingDto> parcels, String trucksSize) {
        parcels.sort(Comparator.comparingInt(ParcelForPackagingDto::getArea).reversed());
        log.trace("Посылки отсортированы по площади в порядке убывания");

        List<Truck> trucks = loadTrucks(parcels, trucksSize);

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
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
                        throw new PackingException("Не удалось разместить посылку");
                    });
                });

        return trucks;
    }
}