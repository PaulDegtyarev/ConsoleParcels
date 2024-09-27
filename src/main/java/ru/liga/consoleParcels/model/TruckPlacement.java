package ru.liga.consoleParcels.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Представляет размещение посылки в грузовике.
 * <p>
 * Содержит информацию о грузовике и позиции, в которой
 * размещена посылка.
 * <p>
 * Класс использует Lombok annotations для автоматической
 * генерации getter-ов, конструкторов и других методов.
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class TruckPlacement {
    private Truck truck;
    private Point position;
}
