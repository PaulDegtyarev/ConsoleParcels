package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.model.Parcel;
import ConsoleParcelsApp.model.Point;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.util.PackageReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SinglePackagingServiceImpl implements PackagingService {
    private PackageReader packageReader;

    public SinglePackagingServiceImpl(PackageReader packageReader) {
        this.packageReader = packageReader;
    }

    @Override
    public List<Truck> packPackages(String filePath) {
        List<Parcel> parcels = packageReader.readPackages(filePath);

        List<Truck> trucks = new ArrayList<>();

        for (Parcel parcel : parcels) {
            Truck truck = new Truck();

            Optional<Point> position = truck.findPosition(parcel);

            truck.place(parcel, position.get().getX(), position.get().getY());

            trucks.add(truck);
        }

        return trucks;
    }
}
