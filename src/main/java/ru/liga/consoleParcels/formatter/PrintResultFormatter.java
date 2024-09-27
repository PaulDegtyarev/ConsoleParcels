package ru.liga.consoleParcels.formatter;

import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

/**
 * Форматтер для вывода результатов упаковки и распаковки на консоль.
 *
 * Этот интерфейс определяет методы для форматирования и вывода
 * результатов упаковки и распаковки в виде строки.
 */
public interface PrintResultFormatter {
    /**
     * Форматирует результаты упаковки и возвращает строку для вывода на консоль.
     *
     * @param trucks Список грузовиков с результатами упаковки.
     * @return Строка с отформатированными результатами упаковки.
     */
    StringBuilder transferPackagingResultsToConsole(List<Truck> trucks);

    /**
     * Форматирует результаты распаковки и возвращает строку для вывода на консоль.
     *
     * @param unPackedTrucks Список грузовиков с результатами распаковки.
     * @return Строка с отформатированными результатами распаковки.
     */
    StringBuilder transferUnpackingResultsToConsole(List<UnPackedTruckDto> unPackedTrucks);
}
