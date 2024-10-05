package ru.liga.consoleParcels.service;

/**
 * Интерфейс для парсинга формы посылок.
 */
public interface ShapeParser {

    /**
     * Парсит форму посылки из строки.
     *
     * @param shape Строка, представляющая форму посылки.
     * @return Двумерный массив символов, представляющий форму посылки.
     */
    char[][] parseShape(String shape);
}
