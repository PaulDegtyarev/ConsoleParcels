package ru.liga.console_parcels.formatter;

import ru.liga.console_parcels.dto.UnpackedTruckDto;
import ru.liga.console_parcels.entity.Truck;

import java.util.List;

/**
 * Интерфейс для форматирования результатов.
 */
public interface ResultFormatter {
    /**
     * Переводит результаты упаковки в строку.
     *
     * @param trucks Список грузовиков.
     * @return Строка с результатами упаковки.
     */
    String convertPackagingResultsToString(List<Truck> trucks);

    /**
     * Переводит результаты распаковки в строку.
     *
     * @param unPackedTrucks Список DTO распакованных грузовиков.
     * @return Строка с результатами распаковки.
     */
    String convertUnpackingResultsToString(List<UnpackedTruckDto> unPackedTrucks);
}
