package ru.liga.console_parcels.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс для представления точки на плоскости.
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Point {
    private int x, y;
}
