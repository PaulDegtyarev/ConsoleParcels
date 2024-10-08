package ru.liga.consoleParcels.formatter.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.UnpackedTruckDto;
import ru.liga.consoleParcels.formatter.PrintResultFormatter;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;
import java.util.Map;

/**
 * Форматирует результаты упаковки и распаковки в строку для вывода в консоль.
 */
@Log4j2
@Service
public class DefaultPrintResultFormatter implements PrintResultFormatter {
    /**
     * Форматирует результаты упаковки в строку.
     *
     * @param trucks Список грузовиков.
     * @return Строка с результатами упаковки.
     */
    @Override
    public StringBuilder transferPackagingResultsToConsole(List<Truck> trucks) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < trucks.size(); i++) {
            int truckId = i + 1;
            log.debug("Печать информации для грузовика {}", truckId);
            Truck actualTruck = trucks.get(i);

            result.append("Грузовик ").append(truckId).append(":\n");

            for (char[] row : actualTruck.getSpace()) {
                result.append("+");
                result.append(new String(row));
                result.append("+\n");
            }


            result.append("+");
            result.append("+".repeat(actualTruck.getTruckWidth()));
            result.append("+\n");
        }

        log.info("Форматирование результатов упаковки завершено для {} грузовиков", trucks.size());
        return result;
    }

    /**
     * Форматирует результаты распаковки в строку.
     *
     * @param unPackedTrucks Список распакованных грузовиков.
     * @return Строка с результатами распаковки.
     */
    @Override
    public StringBuilder transferUnpackingResultsToConsole(List<UnpackedTruckDto> unPackedTrucks) {
        StringBuilder builder = new StringBuilder();

        for (UnpackedTruckDto unPackedTruck : unPackedTrucks) {
            int truckId = unPackedTruck.getTruckId();
            Map<String, Integer> parcelCounts = unPackedTruck.getPackageCountMap();
            List<List<String>> packageLayout = unPackedTruck.getPackageLayout();

            log.debug("Генерация строки для грузовика ID: {}", truckId);

            builder.append("Грузовик ").append(truckId).append(":\n");

            int truckWidth = packageLayout.stream().mapToInt(List::size).max().orElse(0);
            for (List<String> row : packageLayout) {
                builder.append("+");
                for (String packageId : row) {
                    builder.append(packageId);
                }
                builder.append("+\n");
            }
            builder.append("+").append("+".repeat(Math.max(0, truckWidth))).append("+\n");

            builder.append("Количество посылок:\n");
            for (Map.Entry<String, Integer> entry : parcelCounts.entrySet()) {
                String form = entry.getKey();
                int count = entry.getValue();
                builder.append("Форма ").append(form).append(" - ").append(count).append(" шт.\n");
            }

            builder.append("\n");
            log.info("Форматирование завершено для грузовика ID: {}", truckId);
        }

        log.info("Форматирование результатов распаковки завершено для {} грузовиков", unPackedTrucks.size());
        return builder;
    }
}
