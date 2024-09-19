package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.exception.PackingException;
import ConsoleParcelsApp.factory.TruckFactory;
import ConsoleParcelsApp.model.Parcel;
import ConsoleParcelsApp.model.Point;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.util.PackageReader;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

@Log4j2
public class OptimizedPackagingServiceImpl implements PackagingService {
    private PackageReader packageReader;
    private TruckFactory truckFactory;

    public OptimizedPackagingServiceImpl(PackageReader packageReader, TruckFactory truckFactory) {
        this.packageReader = packageReader;
        this.truckFactory = truckFactory;
    }

    @Override
    public List<Truck> packPackages(String filePath, int numberOfCars) {
        log.info("Начало упаковки: файл = {}, количество автомобилей = {}", filePath, numberOfCars);

        List<Parcel> parcels = packageReader.readPackages(filePath);
        log.debug("Прочитано {} посылок из файла {}", parcels.size(), filePath);

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
            log.error("Не удалось разместить посылку: {}", parcel.getId());
            throw new PackingException("Не удалось разместить посылку: " + parcel.getId());
        });
    }
}