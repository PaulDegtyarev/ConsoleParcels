package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.model.Parcel;
import ConsoleParcelsApp.model.Point;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.PackagingService;
import ConsoleParcelsApp.util.PackageReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SinglePackagingServiceImpl implements PackagingService {
    private List<Truck> trucks;
    private PackageReader packageReader = new PackageReader();

    public SinglePackagingServiceImpl() {
        trucks = new ArrayList<>();
    }

    @Override
    public void packPackages(String filePath) throws IOException {
        List<Parcel> parcels = packageReader.readPackages(filePath);

        for (Parcel parcel : parcels) {
            Truck truck = new Truck();

            Point position = truck.findPosition(parcel);

            truck.place(parcel, position.getX(), position.getY());

            trucks.add(truck);
        }
    }

    @Override
    public void printResults() {
        for (int i = 0; i < trucks.size(); i++) {
            System.out.println("Truck " + (i + 1) + ":");

            trucks.get(i).print();
        }
    }

    public List<Truck> getTrucks() {
        return trucks;
    }
}
