package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс DTO для представления ответа с информацией о посылке.
 *
 * <p>
 * Этот класс представляет ответ с информацией о посылке, включая ее имя, форму и символ.
 * Класс предоставляет метод для удобного представления объекта в виде строки.
 * </p>
 */
@Getter
@AllArgsConstructor
public class ParcelResponseDto {
    private final String name;
    private final String shape;
    private final char symbol;

    /**
     * Переопределение метода toString для представления объекта в виде строки.
     *
     * <p>
     * Этот метод форматирует и возвращает строковое представление посылки, включая имя, форму (как двумерный массив символов)
     * и символ, представляющий посылку. Форма посылки представляется в виде строки, где каждый ряд символов отделен переносом строки.
     * </p>
     *
     * @return Строковое представление объекта.
     */
    @Override
    public String toString() {
        return String.format("name=%s,%nshape=%s,%nsymbol=%s", name, shape, symbol);
    }
}
