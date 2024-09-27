package ru.liga.consoleParcels.factory;

/**
 * Фабрика для создания разделителя.
 *
 * Этот интерфейс определяет метод для создания разделителя
 * на основе идентификатора посылки.
 */
public interface DelimeterFactory {
    /**
     * Создает разделитель на основе идентификатора посылки.
     *
     * @param packageId Идентификатор посылки, который будет использован для создания разделителя.
     * @return Целое число, представляющее разделитель.
     */
    int createDelimeter(String packageId);
}
