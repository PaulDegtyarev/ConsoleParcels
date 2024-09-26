package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UnPackedTruckDto {
    private int truckId;
    private Map<String, Integer> packageCounts;
}
