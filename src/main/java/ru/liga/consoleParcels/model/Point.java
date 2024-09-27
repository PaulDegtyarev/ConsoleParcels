package ru.liga.consoleParcels.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Представляет точку на плоскости с координатами x и y.
 * <p>
 * Класс использует Lombok annotations для автоматической генерации
 * getter-ов, конструкторов и других методов.
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Point {
    private int x, y;
}
