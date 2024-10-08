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
    public String transferPackagingResultsToString(List<Truck> trucks) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < trucks.size(); i++) {
            int indexOffset = 1;
            int truckId = i + indexOffset;
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
        return result.toString();
    }

    /**
     * Форматирует результаты распаковки в строку.
     *
     * @param unPackedTrucks Список распакованных грузовиков.
     * @return Строка с результатами распаковки.
     */
    @Override
    public String transferUnpackingResultsToString(List<UnpackedTruckDto> unPackedTrucks) {
        StringBuilder builder = new StringBuilder();


        for (UnpackedTruckDto unPackedTruck : unPackedTrucks) {
            builder.append(buildTruckHeader(unPackedTruck));
            builder.append(buildTruckLayout(unPackedTruck));
            builder.append(buildParcelCounts(unPackedTruck));
            builder.append("\n");}

        log.info("Форматирование результатов распаковки завершено для {} грузовиков", unPackedTrucks.size());
        return builder.toString();
    }

    private String buildTruckHeader(UnpackedTruckDto unPackedTruck) {
        log.debug("Генерация строки для грузовика ID: {}", unPackedTruck.getTruckId());
        return "Грузовик " + unPackedTruck.getTruckId() + ":\n";
    }

    private String buildTruckLayout(UnpackedTruckDto unPackedTruck) {
        int defaultTruckWidth = 0;
        int truckWidth = unPackedTruck.getPackageLayout().stream().mapToInt(List::size).max().orElse(defaultTruckWidth);
        StringBuilder builder = new StringBuilder();

        for (List<String> row : unPackedTruck.getPackageLayout()) {
            builder.append("+");
            for (String packageId : row) {
                builder.append(packageId);
            }
            builder.append("+\n");
        }
        builder.append("+").append("+".repeat(truckWidth)).append("+\n");

        return builder.toString();
    }

    private String buildParcelCounts(UnpackedTruckDto unPackedTruck) {
        StringBuilder builder = new StringBuilder("Количество посылок:\n");

        for (Map.Entry<String, Integer> entry : unPackedTruck.getPackageCountMap().entrySet()) {
            String form = entry.getKey();
            int count = entry.getValue();
            builder.append("Форма ").append(form).append(" - ").append(count).append(" шт.\n");
        }

        log.info("Форматирование завершено для грузовика ID: {}", unPackedTruck.getTruckId());
        return builder.toString();
    }
}
