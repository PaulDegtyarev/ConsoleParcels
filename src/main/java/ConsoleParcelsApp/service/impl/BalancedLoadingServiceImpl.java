package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.model.Parcel;
import ConsoleParcelsApp.model.Point;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.util.PackageReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BalancedLoadingServiceImpl implements PackagingService {
    private PackageReader packageReader;

    public BalancedLoadingServiceImpl(PackageReader packageReader) {
        this.packageReader = packageReader;
    }

    @Override
    public List<Truck> packPackages(String filePath, int numberOfCars) {
        List<Parcel> parcels = packageReader.readPackages(filePath);

        parcels.sort(Comparator.comparingInt(Parcel::getArea).reversed());

        List<Truck> trucks = new ArrayList<>();

        for (int i = 0; i < numberOfCars; i++) {
            trucks.add(new Truck());
        }

        for (Parcel parcel : parcels) {
            Optional<Truck> bestTruck = Optional.empty();
            Optional<Point> bestPosition = Optional.empty();

            for (Truck truck : trucks) {
                Optional<Point> position = truck.findPosition(parcel);
                if (position.isPresent()) {
                    if (bestTruck.isEmpty() || truck.getUsedSpace() < bestTruck.get().getUsedSpace()) {
                        bestTruck = Optional.of(truck);
                        bestPosition = position;
                    }
                }
            }

            if (bestTruck.isPresent()) {
                bestTruck.get().place(parcel, bestPosition.get().getX(), bestPosition.get().getY());
            } else {
                throw new RuntimeException("Не удалось разместить посылку");
            }
        }

        return trucks;
    }
}
