package ru.liga.console_parcels.dto;

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
 *
 * @author Ваше Имя
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UnPackedTruckDto {
    private int truckId;
    private Map<String, Integer> packageCounts;
    private List<List<String>> packageLayout;
}
