package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.model.Parcel;
import ConsoleParcelsApp.model.Point;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.util.PackageReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OptimizedPackagingServiceImpl implements PackagingService {
    private PackageReader packageReader;

    public OptimizedPackagingServiceImpl(PackageReader packageReader) {
        this.packageReader = packageReader;

    }

    @Override
    public List<Truck> packPackages(String filePath) throws IOException {
        List<Parcel> parcels = packageReader.readPackages(filePath);

        parcels.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));

        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck());

        for (Parcel parcel : parcels) {
            System.out.println("Trying to place package " + parcel.getId() + " with height " + parcel.getHeight() + " and width " + parcel.getWidth());

            boolean placed = false;

            for (Truck truck : trucks) {
                Point position = truck.findPosition(parcel);

                if (position != null) {
                    truck.place(parcel, position.getX(), position.getY());
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                Truck newTruck = new Truck();

                Point position = newTruck.findPosition(parcel);

                if (position != null) {
                    newTruck.place(parcel, position.getX(), position.getY());
                    trucks.add(newTruck);
                }
            }
        }

        return trucks;
    }
}