package ru.liga.console_parcels.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс для представления информации о размещении посылки в грузовике.
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class TruckPlacement {
    private Truck truck;
    private Point position;
}
