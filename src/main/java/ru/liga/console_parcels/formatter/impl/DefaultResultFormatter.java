package ru.liga.console_parcels.formatter.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.UnpackedTruckDto;
import ru.liga.console_parcels.formatter.ResultFormatter;
import ru.liga.console_parcels.model.Truck;

import java.util.List;
import java.util.Map;

/**
 * Форматирует результаты упаковки и распаковки в строку для вывода в консоль.
 */
@Log4j2
@Service
public class DefaultResultFormatter implements ResultFormatter {
    /**
     * Форматирует результаты упаковки в строку.
     *
     * @param trucks Список грузовиков.
     * @return Строка с результатами упаковки.
     */
    @Override
    public String convertPackagingResultsToString(List<Truck> trucks) {
        int indexOffset = 1;

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < trucks.size(); i++) {
            int truckId = i + indexOffset;
            log.info("Печать информации для грузовика {}", truckId);
            Truck actualTruck = trucks.get(i);

            result.append(buildTruckHeader(truckId));
            result.append(buildTruckLayout(actualTruck));
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
    public String convertUnpackingResultsToString(List<UnpackedTruckDto> unPackedTrucks) {
        StringBuilder result = new StringBuilder();

        for (UnpackedTruckDto unPackedTruck : unPackedTrucks) {
            result.append(buildTruckHeader(unPackedTruck));
            result.append(buildTruckLayout(unPackedTruck));
            result.append(buildParcelCounts(unPackedTruck));
            result.append("\n");
        }

        log.info("Форматирование результатов распаковки завершено для {} грузовиков", unPackedTrucks.size());
        return result.toString();
    }

    private String buildTruckHeader(int truckId) {
        log.info("Генерация строки для грузовика ID: {}", truckId);
        return String.format("Грузовик %d:%n", truckId);
    }

    private String buildTruckLayout(Truck truck) {
        StringBuilder truckLayout = new StringBuilder();
        for (char[] row : truck.getSpace()) {
            truckLayout.append("+")
                    .append(new String(row))
                    .append("+\n");
        }

        truckLayout.append("+")
                .append("+".repeat(truck.getTruckWidth()))
                .append("+\n");

        return truckLayout.toString();
    }


    private String buildTruckHeader(UnpackedTruckDto unPackedTruck) {
        log.info("Генерация строки для грузовика ID: {}", unPackedTruck.getTruckId());
        return "Грузовик " + unPackedTruck.getTruckId() + ":\n";
    }

    private String buildTruckLayout(UnpackedTruckDto unPackedTruck) {
        int width = calculateTruckWidth(unPackedTruck);

        StringBuilder truckLayout = new StringBuilder();

        for (List<String> row : unPackedTruck.getPackageLayout()) {
            truckLayout.append("+");
            for (String packageId : row) {
                truckLayout.append(packageId);
            }
            truckLayout.append("+\n");
        }
        truckLayout.append("+")
                .append("+".repeat(width))
                .append("+\n");

        return truckLayout.toString();
    }

    private int calculateTruckWidth(UnpackedTruckDto unPackedTruck) {
        int defaultTruckWidth = 0;
        int defaultRowSize = 0;
        int truckWidth = defaultTruckWidth;
        List<List<String>> packageLayout = unPackedTruck.getPackageLayout();

        if (packageLayout != null) {
            int maxRowSize = defaultRowSize;
            for (List<String> row : packageLayout) {
                int rowSize = row.size();
                if (rowSize > maxRowSize) {
                    maxRowSize = rowSize;
                }
            }
            truckWidth = maxRowSize;
        }

        return truckWidth;
    }

    private String buildParcelCounts(UnpackedTruckDto unPackedTruck) {
        StringBuilder parcelCounts = new StringBuilder("Количество посылок:\n");

        for (Map.Entry<String, Integer> entry : unPackedTruck.getPackageCountMap().entrySet()) {
            String form = entry.getKey();
            int count = entry.getValue();
            parcelCounts.append("Форма ").append(form)
                    .append(" - ")
                    .append(count)
                    .append(" шт.\n");
        }

        log.info("Форматирование завершено для грузовика ID: {}", unPackedTruck.getTruckId());
        return parcelCounts.toString();
    }
}
