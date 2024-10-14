package ru.liga.cargomanagement.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Класс DTO для представления количества посылок в грузовике.
 *
 * <p>
 * Этот класс представляет данные о количестве посылок в конкретном грузовике.
 * Он включает информацию о номере грузовика и списке посылок, где каждая посылка представлена {@link ParcelCountDto}.
 * </p>
 */
@Getter
@RequiredArgsConstructor
public class TruckParcelCountDto {
    private final int truckId;
    private final List<ParcelCountDto> parcelCounts;
}
