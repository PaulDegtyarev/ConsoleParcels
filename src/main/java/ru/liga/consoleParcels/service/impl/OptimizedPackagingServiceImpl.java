package ru.liga.consoleParcels.service.impl;

import ru.liga.consoleParcels.dto.ParcelForPackaging;
import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.mapper.ParcelMapper;
import ru.liga.consoleParcels.model.Point;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.PackagingService;
import lombok.extern.log4j.Log4j2;

import java.util.List;

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

    @Override
    public List<Truck> packPackages(List<ParcelForPackaging> parcels, String trucksSize) {
        parcels.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));
        log.trace("Посылки отсортированы по площади в порядке убывания");

        List<Truck> trucks = loadTrucks(parcels, trucksSize);

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private List<Truck> loadTrucks(List<ParcelForPackaging> parcels, String trucksSize) {
        List<Truck> trucks = truckFactory.createTrucks(trucksSize);

        parcels.stream()
                .peek(parcel -> log.trace("Обработка посылки с формой: {}", parcel.getShape()))
                .forEach(parcel -> trucks.stream()
                        .filter(truck -> truck.findPosition(parcel).isPresent())
                        .findFirst()
                        .ifPresentOrElse(truck -> {
                            Point position = truck.findPosition(parcel).orElseThrow();
                            truck.place(parcel, position.getX(), position.getY());
                            log.info("Посылка  размещена в грузовике на позиции ({}, {})", position.getX(), position.getY());
                        }, () -> {
                            throw new PackingException("Не удалось разместить посылку: " + parcel);
                        })
                );

        return trucks;
    }
}