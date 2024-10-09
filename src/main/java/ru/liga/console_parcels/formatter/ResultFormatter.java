package ru.liga.console_parcels.formatter;

import ru.liga.console_parcels.dto.UnpackedTruckDto;
import ru.liga.console_parcels.model.Truck;

import java.util.List;

/**
 * Интерфейс для форматирования результатов упаковки.
 */
public interface ResultFormatter {
    /**
     * Форматирует результаты упаковки в строку.
     *
     * @param trucks Список грузовиков.
     * @return Строка с результатами упаковки.
     */
    String convertPackagingResultsToString(List<Truck> trucks);

    /**
     * Форматирует результаты распаковки в строку.
     *
     * @param unPackedTrucks Список распакованных грузовиков.
     * @return Строка с результатами распаковки.
     */
    String convertUnpackingResultsToString(List<UnpackedTruckDto> unPackedTrucks);
}
