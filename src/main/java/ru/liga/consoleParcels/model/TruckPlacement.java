package ru.liga.consoleParcels.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class TruckPlacement {
    private Truck truck;
    private Point position;
}
