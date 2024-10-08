package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс DTO для представления количества посылок в грузовике.
 *
 * <p>
 * Этот класс представляет данные о количестве посылок в конкретном грузовике.
 * Он включает информацию о номере грузовика и списке посылок, где каждая посылка представлена объектом {@link ParcelCountDto}.
 * </p>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TruckParcelCountDto {
    private int truckId;
    private List<ParcelCountDto> parcelCounts;
}
