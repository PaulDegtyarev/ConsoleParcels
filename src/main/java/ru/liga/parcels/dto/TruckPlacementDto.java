package ru.liga.parcels.dto;

import ru.liga.parcels.model.Point;
import ru.liga.parcels.model.Truck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class TruckPlacementDto {
    private Truck truck;
    private Point position;
}
