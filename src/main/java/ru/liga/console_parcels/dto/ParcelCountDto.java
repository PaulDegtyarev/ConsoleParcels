package ru.liga.console_parcels.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс DTO для представления количества посылок определенной формы.
 *
 * <p>
 * Этот класс представляет данные о количестве посылок определенной формы.
 * Он включает информацию о форме посылки и количестве таких посылок.
 * </p>
 */
@Getter
@RequiredArgsConstructor
public class ParcelCountDto {
    private final String form;
    private final int quantity;
}
