package ru.liga.console_parcels.dto;

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
@RequiredArgsConstructor
public class UnpackedTruckDto {
    private final int truckId;
    private final Map<String, Integer> packageCountMap;
    private final List<List<String>> packageLayout;
}
