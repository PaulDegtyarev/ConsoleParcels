package ru.liga.consoleParcels.factory.impl;

import ru.liga.consoleParcels.factory.DelimeterFactory;

/**
 * Реализация фабрики для создания разделителя.
 * <p>
 * Эта фабрика создает разделитель на основе идентификатора посылки.
 *
 * @see DelimeterFactory
 */
public class DelimeterFactoryImpl implements DelimeterFactory {
    /**
     * Создает разделитель на основе идентификатора посылки.
     *
     * @param packageId Идентификатор посылки, который будет преобразован в целое число и использован как разделитель.
     * @return Целое число, представляющее разделитель.
     */
    @Override
    public int createDelimeter(String packageId) {
        return Integer.parseInt(packageId);
    }
}
