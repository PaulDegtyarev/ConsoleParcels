package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.model.Parcel;
import ConsoleParcelsApp.model.Point;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.util.PackageReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptimizedPackagingServiceImpl implements PackagingService {
    private PackageReader packageReader;

    public OptimizedPackagingServiceImpl(PackageReader packageReader) {
        this.packageReader = packageReader;
    }

    @Override
    public List<Truck> packPackages(String filePath, int numberOfCars) {
        List<Parcel> parcels = packageReader.readPackages(filePath);

        parcels.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));

        List<Truck> trucks = new ArrayList<>();
        for (int i = 0; i < numberOfCars; i++) {
            trucks.add(new Truck());
        }

        for (Parcel parcel : parcels) {
            System.out.println("Trying to place package " + parcel.getId() + " with height " + parcel.getHeight() + " and width " + parcel.getWidth());

            boolean placed = false;

            for (Truck truck : trucks) {
                Optional<Point> position = truck.findPosition(parcel);

                if (position.isPresent()) {
                    truck.place(parcel, position.get().getX(), position.get().getY());
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                throw new RuntimeException("Не удалось разместить посылку");
            }
        }

        return trucks;
    }
}