package ru.liga.console_parcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для представления количества посылок определенной формы.
 *
 * <p>
 * Этот класс представляет данные о количестве посылок определенной формы.
 * Он включает информацию о форме посылки и количестве таких посылок.
 * </p>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelCountDto {
    private String form;
    private int quantity;
}
