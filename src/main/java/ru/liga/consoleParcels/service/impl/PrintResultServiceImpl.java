package ru.liga.consoleParcels.service.impl;

import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.PrintResultService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

@Log4j2
public class PrintResultServiceImpl implements PrintResultService {
    @Override
    public void printPackagingResults(List<Truck> trucks) {
        if (trucks.isEmpty()) {
            log.debug("Посылки не были упакованы");
        } else {
            log.info("Начало печати результатов упаковки для {} грузовиков", trucks.size());

            for (int i = 0; i < trucks.size(); i++) {
                log.debug("Печать информации для грузовика {}", i + 1);
                System.out.println("Truck " + (i + 1) + ":");

                System.out.println(trucks.get(i).toConsoleFormat());
            }

            log.info("Завершение печати результатов упаковки");
        }
    }

    @Override
    public void printUnPackagingResults(List<UnPackedTruckDto> unPackedTrucks) {
        if (unPackedTrucks.isEmpty()) {
            log.debug("Посылки не были распакованы");
        } else {
            log.info("Начало печати результатов распаковки для {} грузовиков", unPackedTrucks.size());

            StringBuilder builder = new StringBuilder();

            for (UnPackedTruckDto unPackedTruck : unPackedTrucks) {
                int truckId = unPackedTruck.getTruckId();
                Map<String, Integer> finalCounts = unPackedTruck.getPackageCounts();

                log.debug("Генерация строки для грузовика ID: {}", truckId);
                builder.append("Грузовик ").append(truckId).append(":\n");

                for (Map.Entry<String, Integer> entry : finalCounts.entrySet()) {
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
            log.info("Завершение печати результатов распаковки");
        }
    }
}
