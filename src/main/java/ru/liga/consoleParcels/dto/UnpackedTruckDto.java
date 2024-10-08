package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Класс DTO для представления распакованного грузовика.
 *
 * <p>
 * Этот класс представляет данные о распакованном грузовике, включая идентификатор грузовика,
 * количество посылок каждого типа и макет раскладки посылок.
 * </p>
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UnpackedTruckDto {
    private int truckId;
    private Map<String, Integer> packageCountMap;
    private List<List<String>> packageLayout;
}
