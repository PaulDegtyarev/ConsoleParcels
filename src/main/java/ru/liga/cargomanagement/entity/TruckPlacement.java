package ru.liga.cargomanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс для представления информации о месте размещения посылки в грузовике.
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class TruckPlacement {
    private Truck truck;
    private ParcelPosition position;
}
