package ru.liga.consoleParcels.formatter;

import ru.liga.consoleParcels.dto.UnpackedTruckDto;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

/**
 * Интерфейс для форматирования результатов упаковки.
 */
public interface PrintResultFormatter {
    /**
     * Форматирует результаты упаковки в строку.
     *
     * @param trucks Список грузовиков.
     * @return Строка с результатами упаковки.
     */
    String transferPackagingResultsToString(List<Truck> trucks);

    /**
     * Форматирует результаты распаковки в строку.
     *
     * @param unPackedTrucks Список распакованных грузовиков.
     * @return Строка с результатами распаковки.
     */
    String transferUnpackingResultsToString(List<UnpackedTruckDto> unPackedTrucks);
}
