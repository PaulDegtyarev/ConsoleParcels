package ru.liga.parcels.service.impl;

import ru.liga.parcels.exception.PackingException;
import ru.liga.parcels.factory.TruckFactory;
import ru.liga.parcels.model.Parcel;
import ru.liga.parcels.model.Point;
import ru.liga.parcels.model.Truck;
import ru.liga.parcels.service.PackagingService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

@Log4j2
public class OptimizedPackagingServiceImpl implements PackagingService {
    private TruckFactory truckFactory;

    public OptimizedPackagingServiceImpl(TruckFactory truckFactory) {
        this.truckFactory = truckFactory;
    }

    @Override
    public List<Truck> packPackages(List<Parcel> parcels, int numberOfCars) {
        parcels.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));
        log.trace("Посылки отсортированы по площади в порядке убывания");

        List<Truck> trucks = truckFactory.createTrucks(numberOfCars);

        parcels.forEach(parcel -> processParcel(parcel, trucks));

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private void processParcel(Parcel parcel, List<Truck> trucks) {
        log.trace("Обработка посылки: {}", parcel.getId());

        Optional<Truck> truckWithSpace = trucks.stream()
                .filter(truck -> truck.findPosition(parcel).isPresent())
                .findFirst();

        truckWithSpace.ifPresentOrElse(truck -> {
            Point position = truck.findPosition(parcel).get();
            truck.place(parcel, position.getX(), position.getY());
            log.info("Посылка {} размещена в грузовике на позиции ({}, {})",
                    parcel.getId(), position.getX(), position.getY());
        }, () -> {
            throw new PackingException("Не удалось разместить посылку: " + parcel);
        });
    }
}