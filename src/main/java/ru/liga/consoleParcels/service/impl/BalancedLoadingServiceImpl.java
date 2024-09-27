package ru.liga.consoleParcels.service.impl;

import ru.liga.consoleParcels.model.TruckPlacement;
import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.model.Point;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.PackagingService;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса упаковки с равномерной загрузкой.
 *
 * Этот сервис сортирует посылки по площади в порядке убывания
 * и затем распределяет их по грузовикам, выбирая для каждой
 * посылки грузовик с наименьшим заполненным пространством.
 *
 * @see PackagingService
 */
@Log4j2
public class BalancedLoadingServiceImpl implements PackagingService {
    private TruckFactory truckFactory;

    /**
     * Конструктор сервиса упаковки с равномерной загрузкой.
     *
     * @param truckFactory Фабрика для создания грузовиков.
     */
    public BalancedLoadingServiceImpl(TruckFactory truckFactory) {
        this.truckFactory = truckFactory;
    }

    /**
     * Упаковывает список посылок в заданное количество грузовиков,
     * стараясь равномерно распределить их по грузовикам.
     *
     * @param parcels      Список посылок, которые нужно упаковать.
     * @param numberOfCars Количество грузовиков, в которые нужно
     *                    упаковать посылки.
     * @return Список грузовиков с упакованными посылками.
     */
    @Override
    public List<Truck> packPackages(List<Parcel> parcels, int numberOfCars) {
        parcels.sort(Comparator.comparingInt(Parcel::getArea).reversed());
        log.trace("Посылки отсортированы по площади в порядке убывания");

        List<Truck> trucks = loadTrucks(parcels, numberOfCars);

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private List<Truck> loadTrucks(List<Parcel> parcels, int numberOfCars) {
        List<Truck> trucks = truckFactory.createTrucks(numberOfCars);

        parcels.stream()
                .peek(parcel -> log.trace("Обработка посылки: {}", parcel.getId()))
                .forEach(parcel -> {
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
                        log.info("Посылка {} размещена в грузовике на позиции ({}, {})",
                                parcel.getId(), position.getX(), position.getY());
                    }, () -> {
                        throw new PackingException("Не удалось разместить посылку: " + parcel.getId());
                    });
                });

        return trucks;
    }
}