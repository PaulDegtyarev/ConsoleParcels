package ru.liga.parcels.service.impl;

import ru.liga.parcels.dto.UnPackedTruckDto;
import ru.liga.parcels.model.Truck;
import ru.liga.parcels.service.PrintResultService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class PrintResultServiceImpl implements PrintResultService {
    @Override
    public void printPackagingResults(Optional<List<Truck>> trucks) {
        trucks.ifPresentOrElse(presentTrucks -> {
            log.info("Начало печати результатов упаковки для {} грузовиков", presentTrucks.size());

            for (int i = 0; i < presentTrucks.size(); i++) {
                log.debug("Печать информации для грузовика {}", i + 1);
                System.out.println("Truck " + (i + 1) + ":");

                presentTrucks.get(i).print();
            }

            log.info("Завершение печати результатов упаковки");
            }, () -> log.debug("Посылки не были упакованы")
        );
    }

    @Override
    public void printUnPackagingResults(Optional<List<UnPackedTruckDto>> unPackedTrucks) {
        unPackedTrucks.ifPresentOrElse(presentUnpackedTruck -> {
            log.info("Начало печати результатов распаковки для {} грузовиков", presentUnpackedTruck.size());

            StringBuilder builder = new StringBuilder();

            for (UnPackedTruckDto unPackedTruck : presentUnpackedTruck) {
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
                },() -> log.debug("Посылки не были распакованы")
        );
    }
}
