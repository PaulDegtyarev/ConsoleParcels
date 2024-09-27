package ru.liga.consoleParcels.formatter.impl;

import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.formatter.PrintResultFormatter;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;
import java.util.Map;

@Log4j2
public class DefaultPrintResultFormatter implements PrintResultFormatter {
    @Override
    public StringBuilder transferPackagingResultsToConsole(List<Truck> trucks) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < trucks.size(); i++) {
            log.debug("Печать информации для грузовика {}", i + 1);

            result.append("Грузовик ").append(i + 1).append(":\n");

            for (char[] row : trucks.get(i).getSpace()) {
                result.append("+");
                result.append(new String(row));
                result.append("+\n");
            }

            result.append("++++++++\n");
            result.append("\n");
        }

        return result;
    }

    @Override
    public StringBuilder transferUnpackingResultsToConsole(List<UnPackedTruckDto> unPackedTrucks) {
        StringBuilder builder = new StringBuilder();

        for (UnPackedTruckDto unPackedTruck : unPackedTrucks) {
            int truckId = unPackedTruck.getTruckId();
            Map<String, Integer> finalCounts = unPackedTruck.getPackageCounts();
            List<List<String>> packageLayout = unPackedTruck.getPackageLayout();

            log.debug("Генерация строки для грузовика ID: {}", truckId);

            builder.append("Грузовик ").append(truckId).append(":");
            builder.append("\n");

            for (List<String> row : packageLayout) {
                builder.append("+");
                for (String packageId : row) {
                    builder.append(packageId);
                }
                builder.append("+\n");
            }

            builder.append("++++++++\n");
            builder.append("Количество посылок:\n");
            for (Map.Entry<String, Integer> entry : finalCounts.entrySet()) {
                String packageId = entry.getKey();
                int count = entry.getValue();

                if (count > 0) {
                    builder.append(packageId).append(" - ").append(count).append(" шт.\n");
                }
            }

            builder.append("\n");
        }

        return builder;
    }
}
