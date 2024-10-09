package ru.liga.console_parcels.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс для представления точки, на которой находится посылка.
 */
@Getter
@RequiredArgsConstructor
public class ParcelPosition {
    private final int x;
    private final int y;
}
