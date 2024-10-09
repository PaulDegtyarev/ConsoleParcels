package ru.liga.console_parcels.service;

/**
 * Интерфейс для управления распаковкой грузовиков.
 */
public interface UnPackagingManager {

    /**
     * Распаковывает посылки из грузовиков и формирует результаты.
     *
     * @param truckFilePath       Путь к файлу с данными грузовиков.
     * @return Строка с результатами распаковки.
     */
    String unpackParcels(String truckFilePath);
}
