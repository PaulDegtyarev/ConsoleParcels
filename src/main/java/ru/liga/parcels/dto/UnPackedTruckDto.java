package ru.liga.parcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UnPackedTruckDto {
    private int truckId;
    private Map<String, Integer> packageCounts;
}
