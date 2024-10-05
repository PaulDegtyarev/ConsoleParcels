package ru.liga.consoleParcels.service;

/**
 * Интерфейс для управления распаковкой грузовиков.
 */
public interface UnPackagingManager {

    /**
     * Распаковывает посылки из грузовиков и формирует результаты.
     *
     * @param truckFilePath       Путь к файлу с данными грузовиков.
     * @param parcelCountFilePath Путь к файлу с данными о количестве посылок.
     * @return Строка с результатами распаковки.
     */
    String unpackParcels(String truckFilePath, String parcelCountFilePath);
}
