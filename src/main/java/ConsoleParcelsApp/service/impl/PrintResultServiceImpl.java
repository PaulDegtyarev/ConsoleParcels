package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.dto.UnPackedTruckDto;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.PrintResultService;

import java.util.List;
import java.util.Map;

public class PrintResultServiceImpl implements PrintResultService {
    @Override
    public void printPackagingResults(List<Truck> trucks) {
        for (int i = 0; i < trucks.size(); i++) {
            System.out.println("Truck " + (i + 1) + ":");

            trucks.get(i).print();
        }
    }

    @Override
    public void printUnPackagingResults(List<UnPackedTruckDto> unPackedTrucks) {
        StringBuilder builder = new StringBuilder();

        for (UnPackedTruckDto unPackedTruck : unPackedTrucks) {
            int truckId = unPackedTruck.getTruckId();
            Map<String, Integer> finalCounts = unPackedTruck.getPackageCounts();


            builder.append("Грузовик ").append(truckId).append(":\n");

            for (java.util.Map.Entry<String, Integer> entry : finalCounts.entrySet()) {
                String packageId = entry.getKey();
                int count = entry.getValue();

                if (count > 0) {
                    builder.append(packageId).append(" - ").append(count).append(" шт.\n");
                } else {
                    builder.append(packageId).append("\n");
                }
            }
        }

        System.out.println(builder);
    }
}
