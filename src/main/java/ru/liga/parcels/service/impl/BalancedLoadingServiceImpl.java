package ru.liga.parcels.service.impl;

import ru.liga.parcels.dto.TruckPlacementDto;
import ru.liga.parcels.exception.PackingException;
import ru.liga.parcels.factory.TruckFactory;
import ru.liga.parcels.model.Parcel;
import ru.liga.parcels.model.Point;
import ru.liga.parcels.model.Truck;
import ru.liga.parcels.service.PackagingService;
import ru.liga.parcels.util.PackageReader;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Log4j2
public class BalancedLoadingServiceImpl implements PackagingService {
    private TruckFactory truckFactory;

    public BalancedLoadingServiceImpl(PackageReader packageReader, TruckFactory truckFactory) {
        this.truckFactory = truckFactory;
    }

    @Override
    public List<Truck> packPackages(List<Parcel> parcels, int numberOfCars) {
        parcels.sort(Comparator.comparingInt(Parcel::getArea).reversed());
        log.trace("Посылки отсортированы по площади в порядке убывания");

        List<Truck> trucks = truckFactory.createTrucks(numberOfCars);

        parcels.forEach(parcel -> processParcel(parcel, trucks));

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private void processParcel(Parcel parcel, List<Truck> trucks) {
        log.trace("Обработка посылки: {}", parcel.getId());

        Optional<TruckPlacementDto> bestPlacement = trucks.stream()
                .map(truck -> {
                    Optional<Point> position = truck.findPosition(parcel);
                    return position.map(point -> new TruckPlacementDto(truck, point));
                })
                .flatMap(Optional::stream)
                .min(Comparator.comparingInt(tp -> tp.getTruck().getUsedSpace()));

        bestPlacement.ifPresentOrElse(tp -> {
            Truck truck = tp.getTruck();
            Point position = tp.getPosition();
            truck.place(parcel, position.getX(), position.getY());
            log.info("Посылка {} размещена в грузовике {} на позиции ({}, {})",
                    parcel, truck, position.getX(), position.getY());
        }, () -> {
            log.error("Не удалось разместить посылку: {}", parcel);
            throw new PackingException("Не удалось разместить посылку: " + parcel);
        });
    }
}