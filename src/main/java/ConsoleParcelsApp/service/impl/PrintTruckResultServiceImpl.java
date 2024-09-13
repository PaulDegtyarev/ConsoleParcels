package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.PrintTruckResultService;

import java.util.List;

public class PrintTruckResultServiceImpl implements PrintTruckResultService {
    @Override
    public void printResults(List<Truck> trucks) {
        for (int i = 0; i < trucks.size(); i++) {
            System.out.println("Truck " + (i + 1) + ":");

            trucks.get(i).print();
        }
    }
}
