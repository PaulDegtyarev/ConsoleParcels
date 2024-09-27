package ru.liga.consoleParcels.service.impl;

import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.model.Point;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.PackagingService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса оптимизированной упаковки.
 * <p>
 * Этот сервис сортирует посылки по площади в порядке убывания
 * и затем пытается разместить каждую посылку в первом
 * найденном свободном пространстве в грузовиках.
 *
 * @see PackagingService
 */
@Log4j2
public class OptimizedPackagingServiceImpl implements PackagingService {
    private TruckFactory truckFactory;

    /**
     * Конструктор сервиса оптимизированной упаковки.
     *
     * @param truckFactory Фабрика для создания грузовиков.
     */
    public OptimizedPackagingServiceImpl(TruckFactory truckFactory) {
        this.truckFactory = truckFactory;
    }

    /**
     * Упаковывает список посылок в заданное количество грузовиков,
     * стараясь максимально заполнить каждый грузовик.
     *
     * @param parcels      Список посылок, которые нужно упаковать.
     * @param numberOfCars Количество грузовиков, в которые нужно
     *                     упаковать посылки.
     * @return Список грузовиков с упакованными посылками.
     */
    @Override
    public List<Truck> packPackages(List<Parcel> parcels, int numberOfCars) {
        parcels.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));
        log.trace("Посылки отсортированы по площади в порядке убывания");

        List<Truck> trucks = loadTrucks(parcels, numberOfCars);

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private List<Truck> loadTrucks(List<Parcel> parcels, int numberOfCars) {
        List<Truck> trucks = truckFactory.createTrucks(numberOfCars);

        parcels.stream()
                .peek(parcel -> log.trace("Обработка посылки: {}", parcel.getId()))
                .forEach(parcel -> trucks.stream()
                        .filter(truck -> truck.findPosition(parcel).isPresent())
                        .findFirst()
                        .ifPresentOrElse(truck -> {
                            Point position = truck.findPosition(parcel).orElseThrow();
                            truck.place(parcel, position.getX(), position.getY());
                            log.info("Посылка {} размещена в грузовике на позиции ({}, {})",
                                    parcel.getId(), position.getX(), position.getY());
                        }, () -> {
                            throw new PackingException("Не удалось разместить посылку: " + parcel);
                        })
                );

        return trucks;
    }
}