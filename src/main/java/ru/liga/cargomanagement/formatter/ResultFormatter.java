package ru.liga.cargomanagement.formatter;

import ru.liga.cargomanagement.dto.UnpackedTruckDto;
import ru.liga.cargomanagement.entity.Truck;

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
