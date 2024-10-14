package ru.liga.cargomanagement.service;

/**
 * Интерфейс для парсинга формы.
 */
public interface ShapeParser {

    /**
     * Парсит форму из строки.
     *
     * @param shape Строка, представляющая форму для парсинга.
     * @return Двумерный массив символов, представляющий форму.
     */
    char[][] parse(String shape);
}
