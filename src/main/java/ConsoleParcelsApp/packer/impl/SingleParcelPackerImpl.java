package ConsoleParcelsApp.packer.impl;

import ConsoleParcelsApp.model.Parcel;
import ConsoleParcelsApp.model.Point;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.packer.ParcelPacker;

import java.util.ArrayList;
import java.util.List;

public class SingleParcelPackerImpl implements ParcelPacker {
    private List<Truck> trucks;

    public SingleParcelPackerImpl() {
        trucks = new ArrayList<>();
    }

    @Override
    public void packPackages(List<Parcel> packages) {
        for (Parcel parcel : packages) {
            Truck truck = new Truck();
            Point position = truck.findPosition(parcel);

            if (position == null) {
                System.out.println("Package " + parcel.getId() + " does not fit in a single truck.");
            } else {
                truck.place(parcel, position.getX(), position.getY());
                trucks.add(truck);
            }
        }
    }

    @Override
    public void printResults() {
        for (int i = 0; i < trucks.size(); i++) {
            System.out.println("Truck " + (i + 1) + ":");
            trucks.get(i).print();
        }
    }
}
