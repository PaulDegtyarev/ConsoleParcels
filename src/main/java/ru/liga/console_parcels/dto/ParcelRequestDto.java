package ru.liga.console_parcels.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Класс DTO для запроса на посылку.
 *
 * <p>
 * Этот класс представляет данные, необходимые для запроса на посылку.
 * Он включает информацию о названии посылки, форме посылки и символе, представляющем посылку.
 * </p>
 */
@Getter
@RequiredArgsConstructor
public class ParcelRequestDto {
    private final String name;
    private final String shape;
    private final char symbol;

    /**
     * Переопределение метода toString для представления объекта в виде строки.
     *
     * @return Строковое представление объекта.
     */
    @Override
    public String toString() {
        return "ParcelRequestDto{" +
                "name='" + name + '\'' +
                ", shape='" + shape + '\'' +
                ", symbol=" + symbol +
                '}';
    }
}